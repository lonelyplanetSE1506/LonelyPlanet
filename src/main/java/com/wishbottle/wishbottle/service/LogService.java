//登录日志服务接口
package com.wishbottle.wishbottle.service;

import com.wishbottle.wishbottle.bean.Log;

import java.util.List;

public interface LogService {
    //查询全部日志
    List<Log>  getAllLog();
    //根据用户名查找登录日志
    List<Log> search(String search);
    //根据用户ID  AccountID查找登录日志
    List<Log> search(Integer search);
    //添加日志，保存日志
    void save(Log log);
 }
