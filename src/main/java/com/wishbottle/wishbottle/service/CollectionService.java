//收藏管理服务接口
package com.wishbottle.wishbottle.service;

import com.wishbottle.wishbottle.bean.Collection;
import java.util.List;
import java.util.Optional;

public interface CollectionService {
    //查询全部收藏
    List<Collection>  getAllCollection();
    //void deleteCollectionByWishID(Integer id);
    //删除收藏
    void deleteCollection(Collection collection);
    //模糊查询
    // 根据用户名、收藏的心愿标题、收藏的心愿内容查询收藏内容
    List<Collection> search(String search);
    //根据收藏者的账号ＩＤ进行查询,我的收藏
    List<Collection> queryMyCollection(Integer accountID);
    //根据collectionID进行查询
    Optional<Collection> findByID(Integer id);
    //保存收藏
    Collection add(Collection acollection);
    //根据AccountID 和WishID查询
    List<Collection> searchByAccountIDAndWishID(Integer accountID,Integer wishID);
    //是否已经收藏过
    boolean hasCollection(Integer accountID,Integer wishID);
}
