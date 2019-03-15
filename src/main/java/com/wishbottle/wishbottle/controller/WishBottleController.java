package com.wishbottle.wishbottle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WishBottleController {
    @GetMapping("/wishBottle")
    public String wishBottlr(){
        if (AccountInfoController.presentAccount.getEmail() != null&&AccountInfoController.presentAccount.getLevel()==3) {
            return "wishBottlePage";
        }
        else
            return "loginPage";
    }

}
