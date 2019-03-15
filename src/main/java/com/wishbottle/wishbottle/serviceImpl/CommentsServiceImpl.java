//评论管理服务类
package com.wishbottle.wishbottle.serviceImpl;
import com.wishbottle.wishbottle.bean.Comments;
import com.wishbottle.wishbottle.repository.CommentsRepository;
import com.wishbottle.wishbottle.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;
    //查询全部评论
    @Override
    public List<Comments> getAllComments() {
        return commentsRepository.findAll();
    }
    //根据评论ID查询评论
    @Override
    public Optional<Comments> findByID(Integer id) {
        return commentsRepository.findById(id);
    }
    //删除评论
    @Override
    public void deleteComment(Comments comments) {
        commentsRepository.delete(comments);
    }
    //模糊查找
    //根据评论者名字、评论的心愿内容或评论内容进行查找
    @Override
    public List<Comments> search(String search) {
        return  commentsRepository.queryBySearch(search);
    }
    //根据WishID查询
    @Override
    public List<Comments> search(Integer search) {
        return  commentsRepository.queryBySearch(search);
    }
    //根据评论者的AccountID进行查找,我发表的评论
    @Override
    public List<Comments> queryByAccountID(Integer accountID) {
        return commentsRepository.queryByAccountID(accountID);
    }
    //联合查找，他人对我的心愿的评论
    @Override
    public List<Comments> queryOtherComment(Integer accountID) {
        return commentsRepository.queryOtherComment(accountID);
    }

    /**
     *
     * @param acomment
     * @return保存一个评论，返回一个评论
     */
    @Override
    public Comments addComment(Comments acomment) {
        return commentsRepository.save(acomment);
    }


}