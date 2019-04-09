//微信用户类
package com.wishbottle.wishbottle.bean;
import javax.persistence.*;
import java.util.Date;

@Entity
public class WeChatAccount {
    @Id//设置主键
    @GeneratedValue//设定增长
    private Integer AccountID;//账号ID

    @Column(unique = true,nullable=false)//唯一值
    private String OpenID;//用户OpenID

    @Column(length = 32)//
    private String NikeName;//昵称，长度为32，

    @Column(length = 50)
    private String SelfIntro;//个人资料

    private Date RegestTime;//注册时间

    public WeChatAccount(String openID) {
        OpenID = openID;
        RegestTime=new Date();
    }
}
