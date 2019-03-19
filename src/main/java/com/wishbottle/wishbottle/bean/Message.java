package com.wishbottle.wishbottle.bean;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {
    @Id
    @GeneratedValue
    private Integer MessageID;//点赞ID
    @Column(nullable = false)
    private Date MsgTime;//消息时间，不允许为空
    @ManyToOne
    private AccountInfo SenderAccountInfo;//发送消息者ID，外键——用户管理
    @ManyToOne
    private AccountInfo ReceiveArAccountInfo;//接受消息者ID，外键——用户管理
    @Column(nullable = false,length =240)
    private String Content;//消息内容

    //构造函数

    public Message(AccountInfo senderAccountInfo, AccountInfo receiveArAccountInfo, String content) {
        MsgTime = new Date();
        SenderAccountInfo = senderAccountInfo;
        ReceiveArAccountInfo = receiveArAccountInfo;
        Content = content;
    }

    public Message() {
    }

    public Integer getMessageID() {
        return MessageID;
    }

    public void setMessageID(Integer messageID) {
        MessageID = messageID;
    }

    public Date getMsgTime() {
        return MsgTime;
    }

    public void setMsgTime(Date msgTime) {
        MsgTime = msgTime;
    }

    public AccountInfo getSenderAccountInfo() {
        return SenderAccountInfo;
    }

    public void setSenderAccountInfo(AccountInfo senderAccountInfo) {
        SenderAccountInfo = senderAccountInfo;
    }

    public AccountInfo getReceiverAccountInfo() {
        return ReceiveArAccountInfo;
    }

    public void setReceiveArAccountInfo(AccountInfo receiveArAccountInfo) {
        ReceiveArAccountInfo = receiveArAccountInfo;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
