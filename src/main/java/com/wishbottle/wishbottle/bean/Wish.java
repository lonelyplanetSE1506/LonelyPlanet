//心愿类
package com.wishbottle.wishbottle.bean;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Wish {
    @Id
    @GeneratedValue
    private Integer WishID;//心愿ID，主键
    @ManyToOne
    private  AccountInfo accountInfo;//发布心愿的用户的ID，外键——用户管理
    @Column(nullable = false,length =80)
    private String Title;//心愿标题
    @Column(nullable = false,length =240)
    private String Content;//心愿内容
    @Column(nullable = false)
    private boolean Permision=true;//心愿权限（仅自己可见:false,所有人可见：true)
    private int CollectionNum= 0;//收藏量
    private  int CommentNum=0;//评论数
    private int GoodNum= 0;//点赞量
    private Date RelTime;//发布时间
    private String picurl=null;//图片url
    private String videourl=null;//视频url
//get,set

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public Integer getWishID() {
        return WishID;
    }

    public void setWishID(Integer wishID) {
        WishID = wishID;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public boolean isPermision() {
        return Permision;
    }

    public String showPermision() {
        if (Permision)
            return "所有人";
        else
            return "仅自己";
    }

    public void setPermision(boolean permision) {
        Permision = permision;
    }
    public boolean getPermision(){
        return Permision;
    }


    public int getCollectionNum() {
        return CollectionNum;
    }

    public void setCollectionNum(int collectionNum) {
        CollectionNum = collectionNum;
    }

    public int getGoodNum() {
        return GoodNum;
    }

    public void setGoodNum(int goodNum) {
        GoodNum = goodNum;
    }

    public Date getRelTime() {
        return RelTime;
    }

    public void setRelTime(Date relTime) {
        RelTime = relTime;
    }

    public int getCommentNum() {
        return CommentNum;
    }

    public void setCommentNum(int commentNum) {
        CommentNum = commentNum;
    }
    public Wish(AccountInfo accountInfo, String title, String content, boolean permision) {
        this.accountInfo = accountInfo;
        Title = title;
        Content = content;
        Permision = permision;
        RelTime = new Date();
    }
    public Wish() {
    }
}
