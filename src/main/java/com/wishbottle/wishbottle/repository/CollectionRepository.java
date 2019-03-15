package com.wishbottle.wishbottle.repository;

import com.wishbottle.wishbottle.bean.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection,Integer> {
    /*@Modifying
    @Query(" from Collection a where a.collectionID=?1")
    public List<Collection > deleteCollectionByID(Integer id);
    */
    //模糊查询
    //根据用户名、收藏的心愿标题、收藏的心愿内容查询收藏内容
    @Query("select a from Collection a where a.accountInfo.NikeName like ?1 " +
            "or a.wish.Title like ?1 or a.wish.Content like ?1")
    public List<Collection> queryBySearch(String search);
    //根据收藏者的账号ＩＤ进行查询,我的收藏
    @Query("select a from Collection a where a.accountInfo.AccountID=?1"  )
    public List<Collection> queryMyCollection(Integer search);
    //根据收藏者的账号ＩＤ和WishID进行查询
    @Query("select a from Collection a where a.accountInfo.AccountID=?1 and a.wish.WishID=?2"  )
    public List<Collection> searchByAccountIDAndWishID(Integer accountID,Integer wishID);
}
