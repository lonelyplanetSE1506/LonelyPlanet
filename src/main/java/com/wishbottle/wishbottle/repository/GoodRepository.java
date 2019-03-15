package com.wishbottle.wishbottle.repository;

import com.wishbottle.wishbottle.bean.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodRepository  extends JpaRepository<Good,Integer> {
    //根据收藏者的账号ＩＤ和WishID进行查询
    @Query("select a from Good a where a.accountInfo.AccountID=?1 and a.wish.WishID=?2"  )
    public List<Good> searchByAccountIDAndWishID(Integer accountID, Integer wishID);
    //根据收藏者的账号ＩＤ进行查询,我的收藏
    @Query("select a from Good a where a.accountInfo.AccountID=?1"  )
    public List<Good> queryMyGood(Integer search);
    @Query("select a from Good a where a.wish.WishID=?1"  )
    public List<Good> searchByWishID(Integer wishID);
}
