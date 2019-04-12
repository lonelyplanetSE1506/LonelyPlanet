package com.wishbottle.wishbottle.controller;

import com.google.gson.Gson;
import com.wishbottle.wishbottle.bean.*;
import com.wishbottle.wishbottle.bean.Collection;
import com.wishbottle.wishbottle.service.*;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class testRestController {
    @Autowired
    AccountInfoService accountInfoService;
    @Autowired
    WeChatAccountService aWeChatAccountService;
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
    public Map<String, Object> addWish(@RequestBody Map<String, Object> body) {
        String wishStr = body.get("wish").toString();

        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(wishStr, map.getClass());

        if (map.containsKey("permision")){
            //如果存在，可以直接put新的键值对。新的键值会自动覆盖之前的。
            if (map.get("permision").equals("true"))
                map.put("permision", true);
            else
                map.put("permision", false);
        }

        Map<String, Object> modelMap = new HashMap<String, Object>();
        System.out.println(body.get("openid").toString());
        System.out.println(wishStr);
        //Wish wish = (Wish)body.get("wish");
        Optional<AccountInfo> aWeChatAccount = accountInfoService.queryByOpenID(body.get("openid").toString());
        if (aWeChatAccount.isPresent())
        {
            System.out.println(aWeChatAccount.get().getWxNikeName());
            Wish wish = new Wish(aWeChatAccount.get(),(String) map.get("title"),(String) map.get("content"),(boolean) map.get("permision"));

            System.out.println(wish.getTitle());
            modelMap.put("success", wishService.addWish(wish));
        }
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

    //改变点赞并返回结果
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

    //改变收藏并返回结果
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

    @GetMapping("/checkCollection/{id}")
    public boolean checkCollection(@PathVariable("id") Integer id) {
        List<Collection> collectionList = collectionService.searchByAccountIDAndWishID(presentAccount.getAccountID(), id);
        Wish awish = wishService.findByID(id).get();
        return !collectionList.isEmpty();
    }

    //添加评论
    @PostMapping("/weChatAddComment")
    public String addComment(@RequestBody Map<String, String> body) {
        Integer wishID = Integer.parseInt(body.get("wishid"));
        String openID = body.get("openid");
        String cmcontent = body.get("cmcontent");

        Wish awish = wishService.findByID(wishID).get();
        System.out.println(awish);

        AccountInfo aWeChatAccount = accountInfoService.queryByOpenID(body.get("openid").toString()).get();
        Comments comment = new Comments(awish,aWeChatAccount,cmcontent);
        awish.setCommentNum(awish.getCommentNum()+1);
        wishService.updateWish(awish);
        commentsService.addComment(comment);
        return "success";
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

    //获取单个心愿
    @GetMapping("/weChatGetOneWish/{wishID}")
    public Map<String, Object> getOneWish(@PathVariable("wishID") Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        Optional<Wish> oneWish = wishService.findByID(id);//根据wishID查询
        map.put("oneWish", oneWish);
        return map;
    }

    //获取我评论的心愿
    @GetMapping("/myCommentWish")
    public Map<String, Object> myCommentWish(){
        Map<String, Object> map = new HashMap<String, Object>();

        List<Wish> myCommentWish = new ArrayList<>();
        List<Comments> myComments = commentsService.queryByAccountID(presentAccount.getAccountID());
        for (Comments mc:myComments) {
            myCommentWish.add(mc.getWish());
            System.out.println(1);
        }
        List<Wish> newList = new ArrayList<Wish>(new HashSet<Wish>(myCommentWish));
        map.put("myCommentWish", newList);
        return map;
    }

    //修改昵称
    @PostMapping("/weChatEditName")
    public String weChatEditName(@RequestBody Map<String, String> map) {
        System.out.println(map.get("name"));
        AccountInfo aWeChatAccount = accountInfoService.queryByOpenID(map.get("openid")).get();
        aWeChatAccount.setWxNikeName(map.get("name"));
        accountInfoService.updateAccountInfo(aWeChatAccount);
        return  "success";
    }


    //获取微信用户账号信息
    @PostMapping("/getWeChatAccountInfo")
    public Map<String, Object> getWeChatAccountInfo(@RequestBody String openid) {
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println(openid);
        Optional<AccountInfo> aWeChatAccount = accountInfoService.queryByOpenID(openid);
        if (aWeChatAccount.isPresent())
        {
            map.put("userInfo", aWeChatAccount.get());
        }
        else
        {
            AccountInfo nWeChatAccount = new AccountInfo(openid);
            accountInfoService.addAccountInfo(nWeChatAccount);
            map.put("userInfo", nWeChatAccount);
        }
        return map;
    }
}
