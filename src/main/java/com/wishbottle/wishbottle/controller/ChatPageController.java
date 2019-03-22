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

        Map<Integer, Object> map = new HashMap<Integer, Object>();

        //我收到的消息
        List<Message> myMessageList = messageService.queryByReceiver(AccountInfoController.presentAccount);

        List<Integer> accountList = new ArrayList<Integer>();
        for (Message msg : myMessageList){
            Integer senderAcountID = msg.getSenderAccountInfo().getAccountID();
            if(accountList.contains(senderAcountID)){
                System.out.println(msg.getSenderAccountInfo().getNikeName());
                accountList.add(senderAcountID);
            }

        }
        for (Integer id:accountList
             ) {
            System.out.println(id.toString());
        }
        System.out.println("hello");

        for (Integer id: accountList) {
            List<Message> newList = myMessageList.stream().filter(msg->msg.getSenderAccountInfo().getAccountID().equals(id)).collect(Collectors.toList());
            map.put(id, newList);
        }


        map.put(1555555, myMessageList);
        map.put(1444444,myMessageList);

        model.addAttribute("myMap",map);
        return returnStr;
    }

}
