package com.wishbottle.wishbottle.repository;

import com.wishbottle.wishbottle.bean.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments,Integer> {
    //模糊查找
    //根据评论者名字、评论的心愿内容或评论内容进行查找
    @Query("select a from Comments a where a.accountInfo.NikeName like ?1 " +
            "or a.wish.Content like ?1 or a.CMContent like ?1")
    public List<Comments> queryBySearch(String search);
    //根据WishID查询
    @Query("select a from Comments a where a.wish.WishID=?1")
    public List<Comments> queryBySearch(Integer search);
    //根据评论者的AccountID进行查找,我发表的评论
    @Query("select a from Comments a where a.accountInfo.AccountID=?1")
    public List<Comments> queryByAccountID(Integer accountID);
    //联合查找，他人对我的心愿的评论
    @Query("select a from Comments a,Wish b where  b.accountInfo.AccountID=?1 and" +
            " a.wish.WishID=b.WishID")
    public List<Comments> queryOtherComment(Integer accountID);
    //@Query("select a from Comments a where a.wish.WishID =?1")
  //  public List<Comments> getCommentsByWishID(Integer WishID);
}
