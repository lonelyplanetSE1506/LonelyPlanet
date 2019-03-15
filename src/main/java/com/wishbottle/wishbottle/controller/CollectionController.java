//收藏控制类
package com.wishbottle.wishbottle.controller;

import com.wishbottle.wishbottle.bean.Collection;
import com.wishbottle.wishbottle.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;
    String searchString = "Search...";

    @GetMapping("/collectPage")//跳转到收藏管理页面
    public String collection(Model model) {
        if (AccountInfoController.presentAccount.getEmail() != null && AccountInfoController.presentAccount.getLevel() <= 2) {
            List<Collection> list = collectionService.getAllCollection();
            model.addAttribute("collections", list);
            model.addAttribute("searchString", searchString);
            model.addAttribute("presentAccount", AccountInfoController.presentAccount);
            return "collectPage";
        } else if (AccountInfoController.presentAccount.getEmail() != null && AccountInfoController.presentAccount.getLevel() == 3)
            return "redirect:/tree";
        else return "loginPage";
    }

    //查询
    @PostMapping("/searchCollection")
    public String searchWish(@RequestParam("searchBox") String searchBox, Model model) {
        if (AccountInfoController.presentAccount.getEmail() != null) {
            if (!searchBox.isEmpty())
                this.searchString = searchBox;
            System.out.println(searchBox);
            List<Collection> collectionList = collectionService.search("%" + this.searchString + "%");
            System.out.println(collectionList.size());
            model.addAttribute("collections", collectionList);
            model.addAttribute("searchString", searchString);
            model.addAttribute("presentAccount", AccountInfoController.presentAccount);
            return "collectPage";
        } else
            return "loginPage";
    }
}
