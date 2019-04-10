package com.wishbottle.wishbottle.repository;

import com.wishbottle.wishbottle.bean.WeChatAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WeChatAccountRepository extends JpaRepository<WeChatAccount,Integer> {

    //根据openid查询微信账号
    @Query("select a from WeChatAccount a where a.OpenID=?1")
    public Optional<WeChatAccount> queryByOpenID(String openid);

}
