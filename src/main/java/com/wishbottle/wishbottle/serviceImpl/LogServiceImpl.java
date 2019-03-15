//登录日志服务类
package com.wishbottle.wishbottle.serviceImpl;

import com.wishbottle.wishbottle.bean.Log;
import com.wishbottle.wishbottle.repository.LogRepository;
import com.wishbottle.wishbottle.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogRepository logRepository;
    //查询全部登录记录
    @Override
    public  List<Log>  getAllLog() {
        return logRepository.findAll();
    }
    //根据用户名查找登录日志
    @Override
    public List<Log> search(String search) {
        return  logRepository.queryBySearch(search);
    }
    //根据用户ID  AccountID查找登录日志
    @Override
    public List<Log> search(Integer search) {
        return  logRepository.queryBySearch(search);
    }
    //添加日志，保存日志
    @Override
    public void save(Log log) {
        logRepository.save(log);
    }
}
