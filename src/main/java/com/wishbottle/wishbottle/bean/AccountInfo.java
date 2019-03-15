//用户信息类
package com.wishbottle.wishbottle.bean;


import javax.persistence.*;
import java.util.Date;
@Entity
public class AccountInfo {
    @Id//设置主键
    @GeneratedValue//设定增长
    private Integer AccountID;//账号ID

    @Column(unique = true,nullable=false,length = 32)//唯一值
    private String NikeName;//昵称，唯一值，长度为32，不允许为空
    @Column(unique = true,nullable=false,length = 20)
    private String Email;//邮箱，唯一值，长度为20，不允许为空
    @Column(length = 20,nullable=false)
    private String Password;//密码，不允许为空，长度为20

    @Column(length = 100)
    private String SelfIntro;//自我介绍，长度为100

    @Column(length = 32,nullable=false)
    private String Avatar;//头像照片路径\src\main\resources\static\assets\img

    private Date RegestTime;//注册时间
    private Date Birthday; //出生日期
    private Integer Level=3;//用户等级，默为3，即普通用户，2位管理员用户，1位超级用户

    public Integer getAccountID() {
        return AccountID;
    }//得到账号ID

    public void setAccountID(Integer accountID) {
        AccountID = accountID;
    }//设置账号ID

    public String getNikeName() {
        return NikeName;
    }//得到昵称

    public void setNikeName(String nikeName) {
        NikeName = nikeName;
    }//设置昵称

    public String getEmail() {
        return Email;
    }//得到邮箱账号

    public void setEmail(String email) {
        Email = email;
    }//设置邮箱账号

    public String getPassword() {
        return Password;
    }//得到密码值

    public void setPassword(String password) {
        Password = password;
    }//设置密码

    public String getSelfIntro() {
        return SelfIntro;
    }//得到自我介绍的内容

    public void setSelfIntro(String selfIntro) {
        SelfIntro = selfIntro;
    }//设置自我介绍

    public String getAvatar() {
        return Avatar;
    }//得到头像路径

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }//设置头像路径

    public Date getRegestTime() {
        return RegestTime;
    }//得到注册时间

    public void setRegestTime(Date regestTime) {
        RegestTime = regestTime;
    }//设置注册时间

    public Date getBirthday() { return Birthday; } //得到出生日期

    public void setBirthday(Date birthday) { Birthday = birthday; } //设置出生日期

    public Integer getLevel() {
        return Level;
    }

    public void setLevel(Integer level) {
        Level = level;
    }

    public AccountInfo() {
    }

    public AccountInfo(String nikeName, String email, String password) {
        NikeName = nikeName;
        Email = email;
        Password = password;
        Avatar="./assets/img/user1.png";
        RegestTime=new Date();
        SelfIntro = "这个人很懒 什么都没有留下";
        Birthday =new Date();
    }
}
