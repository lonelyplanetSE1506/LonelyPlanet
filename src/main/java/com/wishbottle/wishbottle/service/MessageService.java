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
}
