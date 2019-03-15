package com.wishbottle.wishbottle.repository;

import com.wishbottle.wishbottle.bean.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LogRepository extends JpaRepository<Log,Integer> {
    //根据用户名查找登录日志
    @Query("select a from Log a where a.accountInfo.NikeName like ?1 ")
    public List<Log> queryBySearch(String search);
    //根据用户ID  AccountID查找登录日志
    @Query("select a from Log a where a.accountInfo.AccountID= ?1 ")
    public List<Log> queryBySearch(Integer search);
}

