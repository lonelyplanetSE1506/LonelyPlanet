//账号管理服务类
package com.wishbottle.wishbottle.serviceImpl;

import com.wishbottle.wishbottle.bean.WeChatAccount;
import com.wishbottle.wishbottle.repository.WeChatAccountRepository;
import com.wishbottle.wishbottle.service.WeChatAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeChatAccountServiceImpl implements WeChatAccountService {
    @Autowired
    private WeChatAccountRepository aWeChatAccountRepository;

    /**
     * 添加用户信息
     * @param aWeChatAccount
     * @return
     */
    @Override
    public WeChatAccount addWeChatAccount(WeChatAccount aWeChatAccount){
        return aWeChatAccountRepository.save(aWeChatAccount);
    }
    /**
     * 根据openID查询
     * 精确查询
     */
    @Override
    public Optional<WeChatAccount> queryByOpenID(String openid){
        return aWeChatAccountRepository.queryByOpenID(openid);
    }

    /**
     * 查询全部账号
     * @return
     */
    @Override
    public List<WeChatAccount> getAllWeChatAccount(){
        return aWeChatAccountRepository.findAll();
    }

    /**
     * 根据账号ID查询账号
     * @param id
     * @return
     */
    @Override
    public Optional<WeChatAccount> findByID(Integer id){
        return  aWeChatAccountRepository.findById(id);
    }

    /**
     *修改用户信息
     * @param aWeChatAccount
     * @return
     */
    @Override
    public WeChatAccount updateWeChatAccount(WeChatAccount aWeChatAccount) {
        return aWeChatAccountRepository.save(aWeChatAccount);
    }

}
