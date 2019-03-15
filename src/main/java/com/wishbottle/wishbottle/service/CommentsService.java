//评论管理服务接口
package com.wishbottle.wishbottle.service;

import com.wishbottle.wishbottle.bean.Comments;

import java.util.List;
import java.util.Optional;

public interface CommentsService {
    //查询全部评论
    List<Comments> getAllComments();
    //根据评论ID查询评论
    Optional<Comments> findByID(Integer id);
    //删除评论
    void deleteComment(Comments comments);
    //模糊查找
    //根据评论者名字、评论的心愿内容或评论内容进行查找
    List<Comments> search(String search);
    //根据WishID查询
    List<Comments> search(Integer search);
    //根据评论者的AccountID进行查找,我发表的评论
    List<Comments> queryByAccountID(Integer accountID);
    //联合查找，他人对我的心愿的评论
    List<Comments> queryOtherComment(Integer accountID);
   //保存一个评论，返回一个评论
     Comments addComment(Comments acomment);
}
