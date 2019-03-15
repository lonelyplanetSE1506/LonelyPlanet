package com.wishbottle.wishbottle.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 从wish导航到comments
 * 用于显示comments
 */
public class WishToComments {
    private Integer WishID;
    private List<Comments> commentsList;
    //存放WishToComments对象列表，用于显示心愿的评论
    public ArrayList<WishToComments> wishToCommentsList=new ArrayList<>();
    //存放collection对象列表，用于查询是否已经收藏
    public List<Collection> collectionList=new ArrayList<>();
    //存放good对象列表,用于查询是否已经点赞
    public  List<Good> goodList=new ArrayList<>();
    private Integer collectionNum;
    private Integer accountInfoID;

    //判断是否已经收藏，１为已经收藏，０为未收藏
    public Integer hasCollection(Integer wishID){
        //System.out.println(wishID);
        if(!collectionList.isEmpty())
            for(Collection collection:collectionList) {
                if (collection.getWish().getWishID().equals(wishID)
                        && collection.getAccountInfo().getAccountID().equals( accountInfoID)){
                   //// System.out.println(1);
                   // System.out.println(collection.getWish().getTitle());
                         return 1;}
        }
        //System.out.println("collectionList"+collectionList.size());
       // System.out.println(0);
        return 0;
    }
    //判断是否已经点赞，１为已经点赞，０为未点赞
    public Integer hasGood(Integer wishID){
        //System.out.println(wishID);
        if(!goodList.isEmpty())
            for(Good good:goodList) {
                if (good.getWish().getWishID().equals(wishID)
                        && good.getAccountInfo().getAccountID().equals( accountInfoID)){
                           // System.out.println(1);
                             return 1;}

        }
       // System.out.println("goodList"+goodList.size());
        //System.out.println(0);
        return 0;
    }
    // 构造方法
    public WishToComments(Integer wishID, List<Comments> commentsList) {
        WishID = wishID;
        this.commentsList = commentsList;
    }

   //构造方法
    public WishToComments() {
    }

    public List<Comments> getByWishID(int WishID){
        for(WishToComments wishToComments:wishToCommentsList)
            if(wishToComments.getWishID()==WishID)
                return wishToComments.getCommentsList();
        return null;
    }
    public Integer getAccountInfoID() {
        return accountInfoID;
    }

    public void setAccountInfoID(Integer accountInfoID) {
        this.accountInfoID = accountInfoID;
    }

    public Integer getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
    }

    public List<Collection> getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(List<Collection> collectionList) {
        this.collectionList = collectionList;
    }

    public Integer getWishID() {
        return WishID;
    }

    public void setWishID(Integer wishID) {
        WishID = wishID;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public List<Good> getGoodList() {
        return goodList;
    }

    public void setGoodList(List<Good> goodList) {
        this.goodList = goodList;
    }

}
