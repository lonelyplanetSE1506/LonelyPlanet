package com.wishbottle.wishbottle.repository;

import com.wishbottle.wishbottle.bean.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository  extends JpaRepository<Message,Integer> {
    //根据发送者接收者的账号ＩＤ进行查询
    @Query("select a from Message a where a.SenderAccountInfo.AccountID=?1 and a.ReceiverAccountInfo.AccountID=?1"  )
    public List<Message> queryBySenderAndReceiver(Integer SenderID, Integer ReceiverID);

    //模糊查找
    //根据评论者名字、评论的心愿内容或评论内容进行查找
    @Query("select a from Message a where a.SenderAccountInfo.NikeName like ?1 " +
            "or a.Content like ?1")
    public List<Message> queryBySearch(String search);

    //根据ID查询
    @Query("select a from Message a where a.MessageID=?1")
    public List<Message> queryBySearch(Integer search);
}
