package com.wishbottle.wishbottle.controller;

import com.wishbottle.wishbottle.bean.*;
import com.wishbottle.wishbottle.service.CollectionService;
import com.wishbottle.wishbottle.service.CommentsService;
import com.wishbottle.wishbottle.service.GoodService;
import com.wishbottle.wishbottle.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class WishSeaController {
    @Autowired
    private WishService wishService;
    @Autowired
    private CommentsService commentsService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private GoodService goodService;
    String searchString="Search...";
    static List<Wish> top10=new ArrayList<>();//今日点赞量前十的心愿
    static List<Wish> ranWish=new ArrayList<>();//随机产生的十个心愿
    //心愿海
    @GetMapping("/wishSea")
    public String  wishSea(Model model) {
        if (AccountInfoController.presentAccount.getEmail() != null&&AccountInfoController.presentAccount.getLevel()==3) {
            searchString = "Search...";
            //公开的心愿
            List<Wish> list = wishService.getByPermision(true);
            Collections.reverse(list); // 倒序排列
            WishToComments aWishToComment=//初始化，不能为空null
                    list.isEmpty()?new WishToComments():
                            new WishToComments(list.get(0).getWishID(),commentsService.search(list.get(0).getWishID()));
            if(!list.isEmpty())
                for(Wish wish:list)
                    aWishToComment.wishToCommentsList.add(
                            new WishToComments(wish.getWishID(),commentsService.search(wish.getWishID())));
            //我的收藏
            List<Collection> myCollection=collectionService.queryMyCollection(AccountInfoController.presentAccount.getAccountID());
            //model.addAttribute("myCollection",myCollection);
            //我的赞
            List<Good> myGood=goodService.queryMyGood(AccountInfoController.presentAccount.getAccountID());
            aWishToComment.setGoodList(myGood);
            aWishToComment.setCollectionList(myCollection);
            aWishToComment.setAccountInfoID(AccountInfoController.presentAccount.getAccountID());
            top10=wishService.getTop10();//今日点赞量前十的心愿
            ranWish=wishService.getRan10();//随机产生的十个心愿
            model.addAttribute("top10Wishes",top10);
            model.addAttribute("randomWishes", ranWish);
            model.addAttribute("aWishToComment",aWishToComment);
            model.addAttribute("wishes", list);
            model.addAttribute("searchString", searchString);
            model.addAttribute("presentAccount", AccountInfoController.presentAccount);
            return "wishSeaPage";
        } else
            return "loginPage";
    }
    //查询一个心愿
    @GetMapping("/wish{wishID}")
    public String  getoneWish(@PathVariable("wishID")Integer id,Model model) {
        if (AccountInfoController.presentAccount.getEmail() != null && AccountInfoController.presentAccount.getLevel() == 3) {
         //获取心愿
            Wish wish = wishService.findByID(id).get();
            WishToComments aWishToComment =//初始化，不能为空null
                    new WishToComments(wish.getWishID(), commentsService.search(wish.getWishID()));
            //关联所以wish到comment
            aWishToComment.wishToCommentsList.add(
                    new WishToComments(wish.getWishID(), commentsService.search(wish.getWishID())));
            model.addAttribute("presentAccount", AccountInfoController.presentAccount);
            //我的收藏
            List<Collection> myCollection = collectionService.queryMyCollection(AccountInfoController.presentAccount.getAccountID());
            model.addAttribute("myCollection", myCollection);
            //我的赞
            List<Good> myGood = goodService.queryMyGood(AccountInfoController.presentAccount.getAccountID());
            aWishToComment.setGoodList(myGood);
            aWishToComment.setCollectionList(myCollection);
            aWishToComment.setAccountInfoID(AccountInfoController.presentAccount.getAccountID());
            model.addAttribute("aWishToComment", aWishToComment);
            model.addAttribute("Wish", wish);
            model.addAttribute("top10Wishes", top10);
            model.addAttribute("randomWishes", ranWish);
           /*model.addAttribute("id",id);
            return "redirect:/oneWishPage/"+id;*/
          // System.out.println("adddddddddddddddd");
            return "oneWishPage";
        }
        else
            return "redirect:/loginPage";
    }
    //心愿海发表评论
    @PostMapping("/addWishSeaComment")
    public String adddComment(@RequestParam("cmEdit") String cmEdit,
                              @RequestParam("wishID") Integer wishID,
                              Model model){
        if(AccountInfoController.presentAccount.getEmail()!=null){
            Wish awish=wishService.findByID(wishID).get();
            Comments acomment=new Comments(awish,AccountInfoController.presentAccount,cmEdit);
            commentsService.addComment(acomment);//保存评论
            awish.setCommentNum(awish.getCommentNum()+1);//wish的评论数加1
            wishService.updateWish(awish);
            return "redirect:/wishSea";
        }
        else
            return  "redirect:/login";
    }
}
