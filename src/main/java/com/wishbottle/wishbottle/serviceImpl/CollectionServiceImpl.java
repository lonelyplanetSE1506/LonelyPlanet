//收藏管理服务类
package com.wishbottle.wishbottle.serviceImpl;

import com.wishbottle.wishbottle.bean.Collection;
import com.wishbottle.wishbottle.repository.CollectionRepository;
import com.wishbottle.wishbottle.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionRepository collectionRepository;
    //查询全部收藏
    @Override
    public  List<Collection>  getAllCollection() {
        return collectionRepository.findAll();
    }

    /*@Override
    public void deleteCollectionByWishID(Integer id) {
        collectionRepository.deleteCollectionByID(id);
    }*/

    // 删除收藏
    @Override
    public void deleteCollection(Collection collection){
        collectionRepository.delete(collection);
    }
     //模糊查询
    // 根据用户名、收藏的心愿标题、收藏的心愿内容查询收藏内容
    @Override
    public List<Collection> search(String search) {
        return  collectionRepository.queryBySearch(search);
    }
   //根据收藏者的账号ＩＤ进行查询，我的收藏
    @Override
    public List<Collection> queryMyCollection(Integer accountID) {
        return collectionRepository.queryMyCollection(accountID);
    }
   //根据collectionID进行查询
    @Override
    public Optional<Collection> findByID(Integer id) {
        return collectionRepository.findById(id);
    }
    //保存收藏
    @Override
    public Collection add(Collection acollection) {
        return collectionRepository.save(acollection);
    }
    //根据AccountID 和WishID查询
    @Override
   public  List<Collection> searchByAccountIDAndWishID(Integer accountID,Integer wishID){
        return collectionRepository.searchByAccountIDAndWishID(accountID,wishID);
    }
    //是否已经收藏
    @Override
    public boolean hasCollection(Integer accountID,Integer wishID){
        List<Collection> collectionList=collectionRepository.searchByAccountIDAndWishID(accountID,wishID);
        if(!collectionList.isEmpty())
            return true;
        return false;
    }
}
