package com.wishbottle.wishbottle.repository;

import com.wishbottle.wishbottle.bean.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Integer> {
    //模糊查询
    //根据心愿发布者的用户名、心愿标题、心愿内容查询心愿
    @Query("select a from Wish a where a.accountInfo.NikeName like ?1 " +
            "or a.Title like ?1 or a.Content like ?1")
    public List<Wish> queryBySearch(String search);

    //根据心愿id WishID查询心愿
    @Query("select a from Wish a where a.WishID= ?1")
    public List<Wish> queryBySearch(Integer search);

    //根据可见性进行查找（可见性：仅自己可见、公开）
    @Query("select a from Wish a where a.Permision= ?1")
    public List<Wish> queryByPermision(boolean permision);

    //根据心愿发布者的账号id　　AccountID进行查询
    @Query("select a from Wish a where a.accountInfo.AccountID= ?1")
    public List<Wish> queryByAccountID(Integer accountID);

    @Query("select  a from Wish a order by GoodNum DESC")
             public List<Wish> queryOrderByGoodNum();
            //<str_to_date(a.regest_time)>,'%Y-%m-%d %H:%i:%s'))" )
            //">str_to_date('2014/09/09 10:00:00','%Y/%m/%d %H:%i:%s')\n"  +
          //  "            \"and str_to_date(a.regest_time,'%Y-%m-%d %H:%i:%s')<str_to_date('2018/10/10 10:00:00','%Y/%m/%d %H:%i:%s'))\")
            /*
            "str_to_date(a.regest_time,'%Y-%m-%d %H:%i:%s')>str_to_date('2014/09/09 10:00:00','%Y/%m/%d %H:%i:%s')\n" +
            "and str_to_date(a.regest_time,'%Y-%m-%d %H:%i:%s')<str_to_date('2018/10/10 10:00:00','%Y/%m/%d %H:%i:%s'))")*/

}
