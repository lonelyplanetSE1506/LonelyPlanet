//评论控制类
package com.wishbottle.wishbottle.controller;

import com.wishbottle.wishbottle.bean.Comments;
import com.wishbottle.wishbottle.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;
    String searchString = "Search...";

    @GetMapping("/commentPage")//跳转到评论管理页面
    public String comment(Model model) {
        if (AccountInfoController.presentAccount != null && AccountInfoController.presentAccount.getLevel() <= 2) {
            searchString = "Search...";
            List<Comments> list = commentsService.getAllComments();
            model.addAttribute("comments", list);
            model.addAttribute("searchString", searchString);
            model.addAttribute("presentAccount", AccountInfoController.presentAccount);
            return "commentPage";
        } else if (AccountInfoController.presentAccount.getEmail() != null && AccountInfoController.presentAccount.getLevel() == 3)
            return "redirect:/tree";
        else return "loginPage";
    }

    @GetMapping("/deleteComment/{CommentsID}")//删除功能
    public String deletAccount(@PathVariable("CommentsID") Integer id, Model model) {
        if (AccountInfoController.presentAccount.getEmail() != null && AccountInfoController.presentAccount.getLevel() <= 2) {
            searchString = "Search...";
            Optional<Comments> comments = commentsService.findByID(id);
            commentsService.deleteComment(comments.get());
            List<Comments> list = commentsService.getAllComments();
            model.addAttribute("comments", list);
            model.addAttribute("searchString", searchString);
            model.addAttribute("presentAccount", AccountInfoController.presentAccount);
            return "redirect:/commentPage";
        } else if (AccountInfoController.presentAccount.getEmail() != null && AccountInfoController.presentAccount.getLevel() == 3)
            return "redirect:/tree";
        else return "loginPage";
    }

    //查询
    @PostMapping("/searchComment")
    public String searchWish(@RequestParam("searchBox") String searchBox, Model model) {
        if (AccountInfoController.presentAccount.getEmail() != null) {
            List<Comments> commentsList = null;
            if (!searchBox.isEmpty())
                this.searchString = searchBox;
            Pattern pattern = Pattern.compile("[0-9]*");
            //根据WishID进行查询
            if (pattern.matcher(searchString).matches()) {
                Integer in = Integer.valueOf(searchString);
                commentsList = commentsService.search(in);
            } else {
                commentsList = commentsService.search("%" + this.searchString + "%");
            }
            if (!searchBox.isEmpty())
                this.searchString = searchBox;
            commentsList = commentsService.search("%" + this.searchString + "%");
            model.addAttribute("comments", commentsList);
            model.addAttribute("searchString", searchString);
            model.addAttribute("presentAccount", AccountInfoController.presentAccount);
            return "commentPage";
        } else
            return "loginPage";
    }
}