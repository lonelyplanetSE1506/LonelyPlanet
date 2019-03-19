package com.wishbottle.wishbottle.repository;

import com.wishbottle.wishbottle.bean.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository  extends JpaRepository<Message,Integer> {
    //根据收藏者的账号ＩＤ进行查询,我的收藏
    @Query("select a from Message a where a.SenderAccountInfo.AccountID=?1"  )
    public List<Message> queryMyMessage(Integer search);


}
