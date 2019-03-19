package com.wishbottle.wishbottle.serviceImpl;

import com.wishbottle.wishbottle.bean.Message;
import com.wishbottle.wishbottle.repository.MessageRepository;
import com.wishbottle.wishbottle.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Override
    public List<Message> getAllMessage(){
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> findByID(Integer id) {
        return messageRepository.findById(id);
    }

    //删除评论
    @Override
    public void deleteMessage(Message message) {
        messageRepository.delete(message);
    }

    //模糊查找
    //根据评论者名字、评论的心愿内容或评论内容进行查找
    @Override
    public List<Message> search(String search) {
        return  messageRepository.queryBySearch(search);
    }

    //根据ID查询
    @Override
    public List<Message> search(Integer search) {
        return  messageRepository.queryBySearch(search);
    }
    //根据评论者的AccountID进行查找,我发表的评论

}
