//消息控制类
package com.wishbottle.wishbottle.controller;

import com.wishbottle.wishbottle.bean.*;
import com.wishbottle.wishbottle.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


import java.util.Optional;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/")
public class MessageController {
    @Autowired
    private MessageService messageService;
    String searchString="Search...";
    //跳转到消息管理页面
    @GetMapping("/messagePage")
    public String log(Model model){
        if(AccountInfoController.presentAccount.getEmail()!=null&&AccountInfoController.presentAccount.getLevel()<=2) {
            searchString="Search...";
            List<Message> list= messageService.getAllMessage();
            Collections.reverse(list);// 倒序排列
            model.addAttribute("messages",list);
            model.addAttribute("searchString",searchString);
            model.addAttribute("presentAccount",AccountInfoController.presentAccount);
            return "messagePage";
        }
        else if(AccountInfoController.presentAccount.getEmail()!=null&&AccountInfoController.presentAccount.getLevel()==3)
            return "redirect:/tree";
        else return "loginPage";
    }

    @GetMapping("/deleteMessage/{MessagesID}")//删除功能
    public String deletAccount(@PathVariable("MessagesID") Integer id, Model model) {
        if (AccountInfoController.presentAccount.getEmail() != null && AccountInfoController.presentAccount.getLevel() <= 2) {
            searchString = "Search...";
            Optional<Message> message = messageService.findByID(id);
            messageService.deleteMessage(message.get());
            List<Message> list = messageService.getAllMessage();
            model.addAttribute("message", list);
            model.addAttribute("searchString", searchString);
            model.addAttribute("presentAccount", AccountInfoController.presentAccount);
            return "redirect:/messagePage";
        } else if (AccountInfoController.presentAccount.getEmail() != null && AccountInfoController.presentAccount.getLevel() == 3)
            return "redirect:/tree";
        else return "loginPage";
    }

    //查询
    @PostMapping("/searchMessage")
    public String searchWish(@RequestParam("searchBox") String searchBox, Model model) {
        if (AccountInfoController.presentAccount.getEmail() != null) {
            List<Message> messageList = null;
            if (!searchBox.isEmpty())
                this.searchString = searchBox;
            Pattern pattern = Pattern.compile("[0-9]*");
            //根据ID进行查询
            if (pattern.matcher(searchString).matches()) {
                Integer in = Integer.valueOf(searchString);
                messageList = messageService.search(in);
            } else {
                messageList = messageService.search("%" + this.searchString + "%");
            }
            model.addAttribute("messages", messageList);
            model.addAttribute("searchString", searchString);
            model.addAttribute("presentAccount", AccountInfoController.presentAccount);
            return "messagePage";
        } else
            return "loginPage";
    }
}