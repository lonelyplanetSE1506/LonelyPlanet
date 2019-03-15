//登录日志类
package com.wishbottle.wishbottle.bean;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.DatabaseMetaData;
import java.util.Date;

@Entity
public class Log {
    @Id
    @GeneratedValue
    private Integer LogID;//登录日志ID,主键
    @ManyToOne
    private AccountInfo accountInfo;//登录用户ID，外键——用户管理
    @Column(nullable = false,length = 30)
    private String IP;//客户端IP地址
    @Column(nullable = false)
    private Date LogTime;//登录时间
    @Column(nullable=false,length = 100)
    private String Address;//客户端地址

    public Log(AccountInfo accountInfo, String IP, Date logTime, String address) {
        this.accountInfo = accountInfo;
        this.IP = IP;
        LogTime = logTime;
        Address = address;
    }

    //get,set
    public Integer getLogID() {
        return LogID;
    }

    public void setLogID(Integer logID) {
        LogID = logID;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public Date getLogTime() {
        return LogTime;
    }

    public void setLogTime(Date logTime) {
        LogTime = logTime;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Log() {
    }
}
