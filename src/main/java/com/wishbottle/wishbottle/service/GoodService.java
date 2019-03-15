package com.wishbottle.wishbottle.service;

import com.wishbottle.wishbottle.bean.Good;

import java.util.List;

public interface GoodService {
    //根据AccountID 和WishID查询
    List<Good> searchByAccountIDAndWishID(Integer accountID, Integer wishID);
    //保存收藏
    Good add(Good agood);
    //删除收藏
    void deleteGood(Good good);
    //根据点赞者的账号ＩＤ进行查询,我的收藏
    List<Good> queryMyGood(Integer accountID);
    //获取点赞列表
    List<Good> getAllGood();
    //是否已经点赞
    boolean hasGood(Integer accountID,Integer wishID);
    //根据wishID查询good
    List<Good> searchByWishID(Integer wishID);
}
