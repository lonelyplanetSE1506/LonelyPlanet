//账号管理服务接口
package com.wishbottle.wishbottle.service;

import com.wishbottle.wishbottle.bean.AccountInfo;

import java.util.List;
import java.util.Optional;

public interface AccountInfoService {
  // 添加用户信息
  AccountInfo addAccountInfo(AccountInfo accountInfo);
  //根据email或name查询用户账号
  //精确查询
   List<AccountInfo> queryByEmailOrName(String EmailOrName);
  //查询全部账号
   List<AccountInfo> getAllAccountInfo();
  // 根据账号ID查询账号
   Optional<AccountInfo> findByID(Integer id);
   //修改用户信息
    AccountInfo updateAccountInfo(AccountInfo accountInfo);
   //根据email或name查询账号
   //模糊查询
   List<AccountInfo> search(String search);
}
