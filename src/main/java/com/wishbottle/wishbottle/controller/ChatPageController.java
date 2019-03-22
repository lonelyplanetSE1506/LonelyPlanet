//个人主页控制类
package com.wishbottle.wishbottle.controller;

import com.wishbottle.wishbottle.bean.*;
import com.wishbottle.wishbottle.bean.Message;
import com.wishbottle.wishbottle.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class ChatPageController {
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private MessageService messageService;
    //树洞
    @GetMapping("/chat")
    public String chat(Model model){
        if(AccountInfoController.presentAccount.getEmail()!=null&&AccountInfoController.presentAccount.getLevel()==3){
            return returnChat(model,"chatPage");
        }
        else
            return  "redirect:/login";
    }



    //返回我收到的消息
    private String returnChat(Model model,String returnStr){

        Map<AccountInfo, Object> map = new HashMap<AccountInfo, Object>();

        //我收到的消息
        List<Message> myMessageList = messageService.queryByReceiver(AccountInfoController.presentAccount);

        List<AccountInfo> accountList = new ArrayList<AccountInfo>();
        for (Message msg : myMessageList){
            AccountInfo senderAcount = msg.getSenderAccountInfo();
            if(!accountList.contains(senderAcount)){
                accountList.add(senderAcount);
            }

        }

        for (AccountInfo account: accountList) {
            List<Message> newList = myMessageList.stream().filter(msg->msg.getSenderAccountInfo().equals(account)).collect(Collectors.toList());
            map.put(account, newList);
        }

        model.addAttribute("myMap",map);
        return returnStr;
    }

}
