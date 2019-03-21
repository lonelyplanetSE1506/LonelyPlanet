//消息管理服务接口
package com.wishbottle.wishbottle.service;

import com.wishbottle.wishbottle.bean.AccountInfo;
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
    //根据接收者名字、消息内进行查找
    List<Message> search(String search);
    //根据ID查询
    List<Message> search(Integer search);

    //根据接收者ID查询
    List<Message> queryByReceiver(AccountInfo receiverAccountInfo);
}
