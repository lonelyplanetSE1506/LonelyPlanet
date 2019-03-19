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
}
