package com.wishbottle.wishbottle.controller;

import com.wishbottle.wishbottle.bean.*;
import com.wishbottle.wishbottle.bean.Collection;
import com.wishbottle.wishbottle.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class testRestController {
    @Autowired
    AccountInfoService accountInfoService;
    @Autowired
    LogService logService;
    @Autowired
    WishService wishService;
    @Autowired
    CollectionService collectionService;
    @Autowired
    CommentsService commentsService;
    @Autowired
    GoodService goodService;
    static AccountInfo presentAccount = new AccountInfo();

    //登录验证
    @PostMapping("/weChatLogin")
    public boolean addStudent(@RequestBody AccountInfo accountInfo) {
        System.out.println("yesyesyes");
        System.out.println(accountInfo.getPassword());
        System.out.println(accountInfo.getEmail());
        List<AccountInfo> accountInfoList = accountInfoService.queryByEmailOrName(accountInfo.getEmail());
        System.out.println("yesyesyes");
        if (accountInfoList.isEmpty()) {
            System.out.println("无该用户");
            System.out.println("yesyesyes");
            return false;
        } else {
            //登录成功
            if (accountInfoList.get(0).getPassword().equals(accountInfo.getPassword())) {
                presentAccount = accountInfoList.get(0);
                System.out.println(accountInfo.getPassword());
                System.out.println("yesyesyes");
                return true;
            } else {
                System.out.println("账号与密码不匹配！");
                System.out.println("yesyesyes");
                return false;
            }
        }
    }

    //日志记录
    @GetMapping("/weChataddLog/{str}")//ip:location
    public boolean weChataddLog(@PathVariable("str") String str) {
        System.out.println(str);
        String[] re = str.split(":");
        Log log = new Log(presentAccount, re[0], new Date(), re[1]);
        //添加日志v
        // Date date=new Date();
        //Log alog=new Log(presentAccount,ipStr,date,addressStr);
        logService.save(log);
        return false;
    }

    //注册账号
    @PostMapping("/weChatSignup")
    public Integer signup(@RequestBody AccountInfo accountInfo) {
        System.out.println("nonononono");
        System.out.println(accountInfo.getNikeName());
        System.out.println(accountInfo.getEmail());
        System.out.println(accountInfo.getPassword());
        System.out.println("nonononono");
        List<AccountInfo> accountInfoList1 = accountInfoService.queryByEmailOrName(accountInfo.getEmail());
        List<AccountInfo> accountInfoList2 = accountInfoService.queryByEmailOrName(accountInfo.getNikeName());
        //注册成功，返回0
        if (accountInfoList1.isEmpty() && accountInfoList2.isEmpty()) {
            AccountInfo account = new AccountInfo(accountInfo.getNikeName(), accountInfo.getEmail(), accountInfo.getPassword());
            accountInfoService.addAccountInfo(account);
            presentAccount = account;
            System.out.println("yes");
            return 0;
        }
        //email已经注册过,昵称没有注册过，返回1
        else if (accountInfoList1.isEmpty() && !accountInfoList2.isEmpty())
            return 1;
            //昵称已经注册过,email没有注册过，
        else if (!accountInfoList1.isEmpty() && accountInfoList2.isEmpty())
            return 2;
            //昵称和email都注册过
        else return 3;
    }

    //获取登录用户的信息
    @GetMapping("/AUser")
    public Map<String, Object> auser() {
        System.out.println("present");
        Map<String, Object> map = new HashMap<String, Object>();
        List<AccountInfo> accountInfoList = new ArrayList<>();
        accountInfoList.add(presentAccount);
        map.put("userList", accountInfoList);
        return map;
    }

    //获取个人心愿
    @GetMapping("/reWish")
    public Map<String, Object> myWish() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Wish> wishList = wishService.getByAccountID(presentAccount.getAccountID());
        Collections.reverse(wishList);// 倒序排列
        //accountInfoList.add(presentAccount);
        map.put("mywishList", wishList);
        return map;
    }

    //添加心愿
    @PostMapping("/weChatAddWish")
    public Map<String, Object> addWish(@RequestBody Wish wish) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        System.out.println("addWish");
        // System.out.println(wish.getTitle());
        //  System.out.println(wish.getContent());
        //  System.out.println(wish.getPermision());
        wish.setAccountInfo(presentAccount);
        wish.setRelTime(new Date());
        modelMap.put("success", wishService.addWish(wish));
        return modelMap;
    }

    //删除wish
    @GetMapping("/weChatDeleteWish/{id}")
    public boolean delete(@PathVariable("id") Integer id) {
        try {
            wishService.deleteWish(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //我的收藏
    @GetMapping("/reCollection")
    public Map<String, Object> myCollection() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Collection> collectionList = collectionService.queryMyCollection(presentAccount.getAccountID());
        Collections.reverse(collectionList);// 倒序排列
        //accountInfoList.add(presentAccount);
        map.put("myCollectionList", collectionList);
        return map;
    }

    //获取大众心愿
    @GetMapping("/PublicWish")
    public Map<String, Object> publicWish() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<bigwish> abigwishList = new ArrayList<>();
        List<Wish> wishList = wishService.getByPermision(true);//所有人可见
        Collections.reverse(wishList);// 倒序排列
        Integer accountID = presentAccount.getAccountID();
        // isgood(accountID,wishID)
        //isCollection(accountID,wishID)
        // List<Boolean> hasGoodList=new ArrayList<>();
        //List<Boolean> hasCollectionList=new ArrayList<>();
        for (Wish awish : wishList) {
            abigwishList.add(
                    new bigwish(goodService.hasGood(accountID, awish.getWishID()),
                            collectionService.hasCollection(accountID, awish.getWishID()), awish));
            //   hasGoodList.add(goodService.hasGood(accountID,awish.getWishID()));
            /// hasCollectionList.add(collectionService.hasCollection(accountID,awish.getWishID()));
        }
        // map.put("hasGoodList",hasGoodList);
        // map.put("hasCollectionList",hasCollectionList);
        map.put("publicWishList", abigwishList);
        return map;
    }

    //查询comment
    @GetMapping("/weChatgetComment/{id}")
    public Map<String, Object> getComment(@PathVariable("id") Integer id) {
        System.out.println("getComment");
        Map<String, Object> map = new HashMap<String, Object>();
        List<Comments> commentsList = commentsService.search(id);//根据wishID查询
        System.out.println(id);
        System.out.println(commentsList.size());
        //accountInfoList.add(presentAccount);
        map.put("commentsList", commentsList);
        return map;
    }

    //是否已经点赞
    //查询comment
    @GetMapping("/isGood/{id}")
    public boolean isGood(@PathVariable("id") Integer id) {
        List<Good> goodList = goodService.searchByAccountIDAndWishID(presentAccount.getAccountID(), id);
        System.out.println(goodService.hasGood(presentAccount.getAccountID(), id));//true有则删除
        Wish awish = wishService.findByID(id).get();
        if (goodList.isEmpty()) {
            goodService.add(new Good(presentAccount, awish));
            awish.setGoodNum(awish.getGoodNum() + 1);
            wishService.updateWish(awish);
            System.out.println("true");
            //添加成功
            return true;
        } else {
            System.out.println("false");
            goodService.deleteGood(goodList.get(0));
            awish.setGoodNum(awish.getGoodNum() - 1);
            wishService.updateWish(awish);
            //删除成功
            return false;
        }
    }

    //查询collection与collection对应的评论
    @GetMapping("/weChatgetCollection/{id}")
    public Map<String, Object> getCollection(@PathVariable("id") Integer id) {
        System.out.println("getCollection");
        Map<String, Object> map = new HashMap<String, Object>();
        Optional<Collection> collectionList = collectionService.findByID(id);
        System.out.println("collectio");
        System.out.println(collectionList.get().getWish().getTitle());
        //别人对该心愿的评论
        //根据wishid进行查询
        List<Comments> commentsList = commentsService.search(collectionList.get().getWish().getWishID());
        System.out.println("别人对wish的评论");
        System.out.println(commentsList.size());
        //map.put("collectionList",collectionList);
        List<Wish> re = new ArrayList<>();
        re.add(collectionList.get().getWish());
        map.put("wish", re);
        map.put("commentsList", commentsList);
        return map;
    }

    //是否已经收藏
    @GetMapping("/isCollection/{id}")
    public boolean isCollection(@PathVariable("id") Integer id) {
        List<Collection> collectionList = collectionService.searchByAccountIDAndWishID(presentAccount.getAccountID(), id);
        System.out.println(collectionService.hasCollection(presentAccount.getAccountID(), id));//有则true
        Wish awish = wishService.findByID(id).get();
        if (collectionList.isEmpty()) {
            //无，添加成功
            System.out.println("true");
            collectionService.add(new Collection(presentAccount, awish));
            awish.setCollectionNum(awish.getCollectionNum() + 1);
            wishService.updateWish(awish);
            return true;
        } else {
            //有，删除成功
            System.out.println("false");
            collectionService.deleteCollection(collectionList.get(0));
            awish.setCollectionNum(awish.getCollectionNum() - 1);
            wishService.updateWish(awish);
            return false;
        }
    }

    //添加评论
    @GetMapping("/weChatAddComment/{str}")
    public Map<String, Object> addComment(@PathVariable("str") String str) {

        String[] pp=str.split(":");
        String wiship=pp[0];
        System.out.println(wiship);
        String cmcontent=str.substring(wiship.length()+1);
        System.out.println(cmcontent);
        Integer id=Integer.parseInt(wiship);
        Wish awish=wishService.findByID(id).get();
        System.out.println(awish.getWishID());
        Comments comments=new Comments(awish,presentAccount,cmcontent);
        awish.setCommentNum(awish.getCommentNum()+1);
        wishService.updateWish(awish);
        commentsService.addComment(comments);
        Map<String, Object> map = new HashMap<String, Object>();
        List<Comments> commentsList = commentsService.search(id);//根据wishID查询
        System.out.println(commentsList.size());
        //accountInfoList.add(presentAccount);
        map.put("commentsList", commentsList);
        return map;
    }
    //删除collection by collectionid
    @GetMapping("/weChatDeleteCollection/{id}")
    public boolean deleteCollection(@PathVariable("id") Integer id) {
        Optional<Collection> collectionList=collectionService.findByID(id);
        Collection acollection=collectionList.get();
        Wish awish=acollection.getWish();
        System.out.println(awish.getCollectionNum());
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            collectionService.deleteCollection(acollection);
        }
        catch(Exception e){
            return false;
        }
        awish.setCollectionNum(awish.getCollectionNum()-1);//删除一个收藏，wish的收藏数减1
        wishService.updateWish(awish);
        System.out.println(awish.getCollectionNum());
        return true;
    }
}
