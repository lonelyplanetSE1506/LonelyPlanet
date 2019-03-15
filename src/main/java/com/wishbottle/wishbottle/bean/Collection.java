//收藏类
package com.wishbottle.wishbottle.bean;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Collection {
   @Id
   @GeneratedValue
   private Integer collectionID;//收藏ID，主键
    @Column(nullable = false)
    private Date CLTime;//收藏时间，不允许为空
    @ManyToOne
    private AccountInfo accountInfo;//收藏者ID，外键——用户管理
    @ManyToOne
    private Wish wish;//收藏的心愿的ID，外键——心愿管理


    public Integer getCollectionID() {
        return collectionID;
    }//返回收藏ID

   public AccountInfo getAccountInfo() {
        return accountInfo;
    }//返回外键收藏者ID

    public Wish getWish() {
        return wish;
    }//返回外键心愿ID

    public Date getCLTime() {
        return CLTime;
    }//返回收藏时间

    public void setCollectionID(Integer collectionID) {
        this.collectionID = collectionID;
    }//设置收藏ID

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }//设置外键收藏者ID

    public void setWish(Wish wish) {
        this.wish = wish;
    }//设置外键心愿ID

    public void setCLTime(Date CLTime) {
        this.CLTime = CLTime;
    }//设置收藏时间
    //构造方法
    public Collection() {
    }
    //构造方法
    public Collection(AccountInfo accountInfo, Wish wish) {
        this.CLTime = new Date();
        this.accountInfo = accountInfo;
        this.wish = wish;
    }
}
