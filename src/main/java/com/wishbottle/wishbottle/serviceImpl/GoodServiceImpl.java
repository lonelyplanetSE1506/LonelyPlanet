package com.wishbottle.wishbottle.serviceImpl;

import com.wishbottle.wishbottle.bean.Good;
import com.wishbottle.wishbottle.repository.GoodRepository;
import com.wishbottle.wishbottle.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {
    @Autowired
    private GoodRepository goodRepository;
    //根据点赞者的账号ＩＤ和WishID进行查询
    @Override
    public List<Good> searchByAccountIDAndWishID(Integer accountID, Integer wishID) {
        return goodRepository.searchByAccountIDAndWishID(accountID,wishID);
    }
    //添加点赞
    @Override
    public Good add(Good agood) {
        return goodRepository.save(agood);
    }
    //删除点赞
    @Override
    public void deleteGood(Good good) {
        goodRepository.delete(good);
    }

    @Override
    public List<Good> queryMyGood(Integer accountID) {
        return goodRepository.queryMyGood(accountID);
    }
    @Override
    public List<Good> getAllGood(){
        return goodRepository.findAll();
    }
    @Override
    public boolean hasGood(Integer accountID,Integer wishID){
        List<Good> wishes=goodRepository.searchByAccountIDAndWishID(accountID,wishID);
        if(!wishes.isEmpty())
            return true;
        return false;
    }

    @Override
    public List<Good> searchByWishID(Integer wishID) {
        return goodRepository.searchByWishID(wishID);
    }
}
