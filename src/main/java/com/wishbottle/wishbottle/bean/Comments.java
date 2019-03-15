//评论类
package com.wishbottle.wishbottle.bean;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comments {
    @Id
    @GeneratedValue
    private Integer CMID;//评论ID，主键
     @ManyToOne
     private Wish wish;//被评论的心愿的ID，外键——心愿管理
     @ManyToOne
     private AccountInfo accountInfo;//评论者ID，外键——用户管理
    @Column(nullable = false,length = 240)
    private String CMContent;//评论内容
    @Column(nullable = false)
    private Date CMTime;//评论时间


//get，set
    public Integer getCMID() {
        return CMID;
    }

    public void setCMID(Integer CMID) {
        this.CMID = CMID;
    }

    public Wish getWish() {
        return wish;
    }

    public void setWish(Wish wish) {
        this.wish = wish;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public String getCMContent() {
        return CMContent;
    }

    public void setCMContent(String CMContent) {
        this.CMContent = CMContent;
    }

    public Date getCMTime() {
        return CMTime;
    }

    public void setCMTime(Date CMTime) {
        this.CMTime = CMTime;
    }

    public Comments() {
    }

    public Comments(Wish wish, AccountInfo accountInfo, String CMContent) {
        this.wish = wish;
        this.accountInfo = accountInfo;
        this.CMContent = CMContent;
        this.CMTime = new Date();
    }
}
