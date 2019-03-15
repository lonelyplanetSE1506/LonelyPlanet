//个人主页控制类
package com.wishbottle.wishbottle.controller;

import com.wishbottle.wishbottle.bean.*;
import com.wishbottle.wishbottle.bean.Collection;
import com.wishbottle.wishbottle.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/")
public class PersonController {
    private Integer wishNum,goodNum,commentNum;
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private WishService wishService;
    @Autowired
    private CommentsService commentsService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private GoodService goodService;
    //树洞
    @GetMapping("/tree")
    public String tree(Model model){
        if(AccountInfoController.presentAccount.getEmail()!=null&&AccountInfoController.presentAccount.getLevel()==3){
            return returnTree(model,"treePage");
        }
        else
            return  "redirect:/login";
    }
    //修改用户信息
    @PostMapping("/editPost")
    public String updateAccountInfo(@RequestParam("userNameEdit") String name,
                                    @RequestParam("emailEdit") String email,
                                    @RequestParam("birthdayEdit") Date birthday,
                                    @RequestParam("introEdit") String introEdit,
                                    Model model) {
        if(AccountInfoController.presentAccount.getEmail()!=null){
            List<AccountInfo> accountInfoList1=accountInfoService.queryByEmailOrName(name);
            List<AccountInfo> accountInfoList2=accountInfoService.queryByEmailOrName(email);
            if((accountInfoList1.size()==0&&accountInfoList2.size()==0)||//name,email都是不在数据库的
                (accountInfoList1.size()==0&&accountInfoList2.size()==1
                        &&email.equals(AccountInfoController.presentAccount.getEmail()))||//name不再数据库中,email为原来的
                accountInfoList1.size()==1&&accountInfoList2.size()==0
                        &&name.equals(AccountInfoController.presentAccount.getNikeName())||//email不再数据库中,name为原来的
                accountInfoList1.size()==1&&accountInfoList2.size()==1
                        &&name.equals(AccountInfoController.presentAccount.getNikeName())
                        &&email.equals(AccountInfoController.presentAccount.getEmail())) //name，email都为原来的
            {
                AccountInfoController.presentAccount.setBirthday(birthday);
                AccountInfoController.presentAccount.setSelfIntro(introEdit);
                AccountInfoController.presentAccount.setNikeName(name);
                AccountInfoController.presentAccount.setEmail(email);
            }
                accountInfoService.updateAccountInfo(AccountInfoController.presentAccount);
               // System.out.println(AccountInfoController.presentAccount.getAccountID() + "  " + accountInfo.getAccountID());
            return "redirect:/tree";
        }
        else
            return  "redirect:/login";
    }
    //删除个人心愿
    @GetMapping("/deleteMyWish/{WishID}")
    public String deletMyWish(@PathVariable("WishID") Integer id, Model model){
        if(AccountInfoController.presentAccount.getEmail()!=null&&AccountInfoController.presentAccount.getLevel()==3){
           // System.out.println("id"+id);
            Optional<Wish> wishs =wishService.findByID(id);

            //delete collection
            List<Collection> collectionList=collectionService.getAllCollection();
           if(!collectionList.isEmpty())
                for(Collection acollection :collectionList)
                     if(acollection.getWish().getWishID()==id)
                            collectionService.deleteCollection(acollection);

            //delete comment
            //commentsService.getAllComments();
            List<Comments> commentsList=commentsService.getAllComments();
            if(!commentsList.isEmpty())
                //System.out.println("commentList is empty");
                for(Comments acomment:commentsList){
                //System.out.println(acomment. getCMID());
                   if(acomment.getWish().getWishID()==id)
                        commentsService.deleteComment(acomment);}
            //delet good
            List<Good> goodList=goodService.getAllGood();
            if(!goodList.isEmpty())
                for(Good agood:goodList)
                    if(agood.getWish().getWishID()==id)
                        goodService.deleteGood(agood);
            wishService.deleteWish(wishs.get());
            return "redirect:/tree";
        }

        else
            return  "redirect:/login";
    }
    //删除发布的评论
    @GetMapping("/deleteMyComment/{CommentID}")
    public String deletMyComment(@PathVariable("CommentID") Integer id, Model model){
        if(AccountInfoController.presentAccount.getEmail()!=null&&AccountInfoController.presentAccount.getLevel()==3){
            Optional<Comments> comments =commentsService.findByID(id);
            commentsService.deleteComment(comments.get());
            return "redirect:/tree";
        }
        else
            return  "redirect:/login";
    }
    //删除收藏心愿
    @GetMapping("/deleteMyCollection/{CollectionID}")
    public String deletMyCollection(@PathVariable("CollectionID") Integer id, Model model){
        if(AccountInfoController.presentAccount.getEmail()!=null&&AccountInfoController.presentAccount.getLevel()==3){
            Optional<Collection> collection =collectionService.findByID(id);
            collectionService.deleteCollection(collection.get());
            return "redirect:/tree";
        }
        else
            return  "redirect:/login";
    }
    //发表评论
    @PostMapping("/addComment")
    public String adddComment(@RequestParam("cmEdit") String cmEdit,
                              @RequestParam("wishID") Integer wishID,
                              Model model){
        if(AccountInfoController.presentAccount.getEmail()!=null){
            Wish awish=wishService.findByID(wishID).get();
            Comments acomment=new Comments(awish,AccountInfoController.presentAccount,cmEdit);
            commentsService.addComment(acomment);//保存评论
            awish.setCommentNum(awish.getCommentNum()+1);//wish的评论数加1
            wishService.updateWish(awish);
            return "redirect:/tree";
        }
        else
            return  "redirect:/login";
    }
    //添加或删除收藏
    @PostMapping("/collect")
    public String addCollection(@RequestParam("collectWishID") Integer WishID,Model model){
        //if(AccountInfoController.presentAccount.getEmail()!=null){
            List<Collection> collectionList=collectionService.searchByAccountIDAndWishID(
                    AccountInfoController.presentAccount.getAccountID(),WishID);
            Wish awish=wishService.findByID(WishID).get();
            if(collectionList.isEmpty())
            {
                Collection acollection=new Collection(AccountInfoController.presentAccount,awish);
                collectionService.add(acollection);
                awish.setCollectionNum(awish.getCollectionNum()+1);
                wishService.updateWish(awish);
            }
            else {
                collectionService.deleteCollection(collectionList.get(0));
                awish.setCollectionNum(awish.getCollectionNum()-1);
                wishService.updateWish(awish);
            }
            return "redirect:/tree";
    }
    //添加或删除点赞
    @PostMapping("/good")
    public String addGood(@RequestParam("goodWishID") Integer WishID,Model model){
        //if(AccountInfoController.presentAccount.getEmail()!=null){
        List<Good> goodList=goodService.searchByAccountIDAndWishID(
                AccountInfoController.presentAccount.getAccountID(),WishID);
        //System.out.println(goodList.size());
        Wish awish=wishService.findByID(WishID).get();
        if(goodList.isEmpty())
        {
            Good agood=new Good(AccountInfoController.presentAccount,awish);
            goodService.add(agood);
            awish.setGoodNum(awish.getGoodNum()+1);
            wishService.updateWish(awish);

        }
        else {
            goodService.deleteGood(goodList.get(0));
            awish.setGoodNum(awish.getGoodNum()-1);
            wishService.updateWish(awish);
        }
        //System.out.println(awish.getGoodNum());
        return "redirect:/tree";
    }
    //返回心愿、心愿找评论、评论、被评论和收藏的list方法
    private String returnTree(Model model,String returnStr){
        //我的心愿
        List<Wish> wishList=wishService.getByAccountID(AccountInfoController.presentAccount.getAccountID());
        Collections.reverse(wishList);// 倒序排列
        model.addAttribute("myWish",wishList);

        //获取全部心愿
        List<Wish> allWishList=wishService.getAllWish();
        Collections.reverse(allWishList);// 倒序排列
        WishToComments aWishToComment=//初始化，不能为空null
                wishList.isEmpty()?new WishToComments():
                new WishToComments(wishList.get(0).getWishID(),commentsService.search(wishList.get(0).getWishID()));
        //关联所以wish到comment
        if(!allWishList.isEmpty())
            for(Wish wish:allWishList)
                aWishToComment.wishToCommentsList.add(
                    new WishToComments(wish.getWishID(),commentsService.search(wish.getWishID())));

        //点赞总数
        int goodNum=0;
        for(Wish awish:wishList){
            goodNum+=awish.getGoodNum();
        }
        model.addAttribute("goodNum",goodNum);
        //我的评论
        List<Comments> myList=commentsService.queryByAccountID(AccountInfoController.presentAccount.getAccountID());
        model.addAttribute("myComments",myList);
        //我评论的心愿
        List<Wish> myCommentWish=new ArrayList<>();
        Integer accountID=AccountInfoController.presentAccount.getAccountID();
        for(WishToComments aa:aWishToComment.wishToCommentsList)//对所有wishToComment进行遍历
            for(Comments cc:aa.getCommentsList())//遍历comments,当评论的accountID为本人ID加入myCommentWish
                if(cc.getAccountInfo().getAccountID().equals(accountID)){
                myCommentWish.add(wishService.findByID(cc.getWish().getWishID()).get());
                break;
                }
        model.addAttribute("myCommentWish",myCommentWish);
        //测试
        //for(Wish awish:myCommentWish)
       // {
       //     System.out.println(awish.getWishID());
       // }
        //对我的评论
        List<Comments> otherList=commentsService.queryOtherComment(AccountInfoController.presentAccount.getAccountID());
        model.addAttribute("otherComments",otherList);
        model.addAttribute("presentAccount",AccountInfoController.presentAccount);
        //我的收藏
        List<Collection> myCollection=collectionService.queryMyCollection(AccountInfoController.presentAccount.getAccountID());
        Collections.reverse(myCollection);// 倒序排列
        model.addAttribute("myCollection",myCollection);


        //我的赞
        List<Good> myGood=goodService.queryMyGood(AccountInfoController.presentAccount.getAccountID());
        aWishToComment.setGoodList(myGood);
        aWishToComment.setCollectionList(myCollection);
        aWishToComment.setAccountInfoID(AccountInfoController.presentAccount.getAccountID());
        model.addAttribute("aWishToComment",aWishToComment);
        return returnStr;
    }
    //处理文件上传
    @RequestMapping(value="/addWish", method = RequestMethod.POST)
    public String addWish(@RequestParam("wishTitle") String wishTitle,
                          @RequestParam("wishContent") String wishContent,
                          @RequestParam("visibility") String visibility,
                          @RequestParam("pic") MultipartFile pic,
                           @RequestParam("video") MultipartFile video,
                          Model model,HttpServletRequest request) {
        boolean  Permision=visibility.equals("all");
        Wish awish=new Wish(AccountInfoController.presentAccount,wishTitle,wishContent,Permision);
        //String contentType = pic.getContentType();//文件类型
        //String prefix=pic.getOriginalFilename().substring(pic.getOriginalFilename().lastIndexOf(".")+1);//获取后缀
        //System.out.println(prefix);
        String picName =(new Date()).getTime()+"."+pic.getOriginalFilename().substring(pic.getOriginalFilename().lastIndexOf(".")+1);//picture文件名字
        String videoName =(new Date()).getTime()+"."+video.getOriginalFilename().substring(video.getOriginalFilename().lastIndexOf(".")+1);//video文件名字
        //System.out.println((new Date()).getTime());
        //System.out.println(picName);
        //System.out.println(videoName);
        if(!pic.getOriginalFilename().isEmpty()||!video.getOriginalFilename().isEmpty()) {
            //System.out.println("pappappapappapa");
            //获取跟目录
            File path = null;
            try {
                path = new File(ResourceUtils.getURL("classpath:").getPath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (!path.exists()) path = new File("");
                 System.out.println("path:" + path.getAbsolutePath());
            //上传目录为/src/main/resources/static/assets/...，则可以如下获取：
            File picurl = new File(path.getAbsolutePath(), "/src/main/resources/static/assets/img/ /");
            File videourl = new File(path.getAbsolutePath(), "/src/main/resources/static/assets/video/ /");
            if (!picurl.exists()) picurl.mkdirs();
            if (!videourl.exists()) videourl.mkdirs();
            System.out.println("picture url:" + picurl.getAbsolutePath());
            System.out.println("video  url:" + videourl.getAbsolutePath());
            String picPath = picurl.getAbsolutePath();
            String videoPath = videourl.getAbsolutePath();
            try {
                if (!pic.getOriginalFilename().isEmpty()) {
                    FileUtil.uploadFile(pic.getBytes(), picPath, picName);
                    awish.setPicurl("./assets/img/" + picName);
                }
                if (!video.getOriginalFilename().isEmpty()) {
                    FileUtil.uploadFile(video.getBytes(), videoPath, videoName);
                    awish.setVideourl("./assets/video/" + videoName);
                }
            } catch (Exception e) {
            }
        }
        wishService.addWish(awish);
        return "redirect:/tree";
    }
}
