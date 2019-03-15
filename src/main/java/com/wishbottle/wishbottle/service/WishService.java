//心愿管理服务接口
package com.wishbottle.wishbottle.service;

import com.wishbottle.wishbottle.bean.Wish;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WishService {
    //查询全部心愿
    List<Wish>  getAllWish();
    //按照WishID进行查询
    Optional<Wish> findByID(Integer id);
    //删除心愿
    void deleteWish(Wish wish);
    //删除
    void deleteWish(Integer id);
    //模糊查询
    //根据心愿发布者的用户名、心愿标题、心愿内容查询心愿
    List<Wish> search(String search);
    //根据心愿id WishID查询心愿
    List<Wish> search(Integer search);
    //添加心愿
    Wish addWish(Wish wish);
    //根据可见性进行查找（可见性：仅自己可见、公开）
    List<Wish> getByPermision(boolean permisiom);
    //根据心愿发布者的账号id　　AccountID进行查询
    List<Wish> getByAccountID(Integer accountID);
    //更新心愿,修改心愿
    Wish updateWish(Wish awish);
    //获取点赞量前十的
    List<Wish> getTop10();
    //随机获取10个心愿
    List<Wish> getRan10();

}