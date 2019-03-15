package com.wishbottle.wishbottle.repository;

import com.wishbottle.wishbottle.bean.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountInfoRepository extends JpaRepository<AccountInfo,Integer> {
    /**
     * 根据email或name查询用户账号
     * 精确查询
     * @param EmailOrName
     * @return
     */
    @Query("select a from AccountInfo a where a.Email=?1 or a.NikeName=?1")
    public List<AccountInfo> queryByEmailOrName(String EmailOrName);

    /**
     * 根据email或name查询用户账号
     * 模糊查询
     * @param search
     * @return
     */
    @Query("select a from AccountInfo a where a.NikeName like ?1 " +
            "or a.Email like ?1")
    public List<AccountInfo> queryBySearch(String search);
}
