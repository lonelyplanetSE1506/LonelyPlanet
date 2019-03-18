package com.wishbottle.wishbottle.bean;

import javax.persistence.*;
import java.util.Date;

@Entity
public class    Good {
    @Id
    @GeneratedValue
    private Integer GoodID;//点赞ID
    @Column(nullable = false)
    private Date CLTime;//收藏时间，不允许为空
    @ManyToOne
    private AccountInfo accountInfo;//收藏者ID，外键——用户管理
    @ManyToOne
    private Wish wish;//收藏的心愿的ID，外键——心愿管理
    //构造函数
    public Good(AccountInfo accountInfo, Wish wish) {
        this.CLTime = new Date();
        this.accountInfo = accountInfo;
        this.wish = wish;
    }

    public Good() {
    }

    public Integer getGoodID() {
        return GoodID;
    }

    public void setGoodID(Integer goodID) {
        GoodID = goodID;
    }

    public Date getCLTime() {
        return CLTime;
    }

    public void setCLTime(Date CLTime) {
        this.CLTime = CLTime;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public Wish getWish() {
        return wish;
    }

    public void setWish(Wish wish) {
        this.wish = wish;
    }
}
