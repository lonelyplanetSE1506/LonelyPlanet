package com.wishbottle.wishbottle.controller;

import com.wishbottle.wishbottle.pojo.ChatResult;
import com.wishbottle.wishbottle.pojo.WxLoginResult;
import com.wishbottle.wishbottle.utils.FastJsonUtils;
import com.wishbottle.wishbottle.utils.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Queue;
import java.util.Random;

@RestController
@RequestMapping("chat")
public class ChatController {
    /*appid: "wxd33cf730dd51c008",
    secret: "d92cdb9483a87a4fcbf485840308e53a",*/
    private Integer count;
    private String SECRET = "d92cdb9483a87a4fcbf485840308e53a";
    private String APPID = "wxd33cf730dd51c008";
    private String JS_CODE;
    @RequestMapping(value = "/random",method = RequestMethod.POST)
    public ChatResult randomChat(){

        //用户随机分配聊天室
        Random random  = new Random();
        int i = random.nextInt(3);
        i = 1;
        return new ChatResult(100,"分配成功",i);

    }
    @PostMapping("/login/{code}")
    public ChatResult auth(@PathVariable("code")String code){
             WxLoginResult wxLoginResult = null;
            JS_CODE = code;
            String msg;
            String loginValidURL = "https://api.weixin.qq.com/sns/jscode2session?appid="+APPID+"&secret="+SECRET+"&js_code="+JS_CODE+"&grant_type=authorization_code";
            try {
                wxLoginResult = FastJsonUtils.toBean(HttpRequestUtil.get(loginValidURL), WxLoginResult.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        System.out.println("openid:"+ wxLoginResult.getOpenid());
            return new ChatResult(200,"success",null);
        }
}
