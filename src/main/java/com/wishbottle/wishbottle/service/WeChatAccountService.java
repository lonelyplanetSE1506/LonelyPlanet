//账号管理服务接口
package com.wishbottle.wishbottle.service;

import com.wishbottle.wishbottle.bean.WeChatAccount;

import java.util.List;
import java.util.Optional;

public interface WeChatAccountService {
    // 添加用户信息
    WeChatAccount addWeChatAccount(WeChatAccount accountInfo);

    //通过openid查询
    Optional<WeChatAccount> queryByOpenID(String openid);
    //查询全部账号
    List<WeChatAccount> getAllWeChatAccount();
    // 根据账号ID查询账号
    Optional<WeChatAccount> findByID(Integer id);
    //修改用户信息
    WeChatAccount updateWeChatAccount(WeChatAccount accountInfo);
}
