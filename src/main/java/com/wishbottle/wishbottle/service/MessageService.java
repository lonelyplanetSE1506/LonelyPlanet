//消息管理服务接口
package com.wishbottle.wishbottle.service;

import com.wishbottle.wishbottle.bean.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    //查询全部消息
    List<Message> getAllMessage();
    //根据消息ID查询消息
    Optional<Message> findByID(Integer id);
    //删除消息
    void deleteMessage(Message message);

    //模糊查找
    //根据评论者名字、评论的心愿内容或评论内容进行查找
    List<Message> search(String search);
    //根据ID查询
    List<Message> search(Integer search);

    //根据发送者和接收者ID查询
    List<Message> queryBySenderAndReceiver(Integer SenderID, Integer ReceiverID);
}
