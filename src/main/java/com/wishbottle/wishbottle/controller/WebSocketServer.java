package com.wishbottle.wishbottle.controller;

import com.wishbottle.wishbottle.pojo.Server2ClientMessage;
import com.wishbottle.wishbottle.utils.FastJsonUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{id}/{to}")
@Component
public class WebSocketServer {
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet =
            new CopyOnWriteArraySet<WebSocketServer>();
    private Session session;
    public String id;
    public String to;
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("id") String id,
                       @PathParam("to")String to) throws IOException {
        this.session = session;
        this.id = id;
        this.to = to;
        webSocketSet.add(this);
        onlineAdd();

    }
    @OnMessage
    public void onMessage(String message,Session session) throws IOException {
        System.out.println(message);
        webSocketSet.stream()
                .filter(e->e.to.equals(this.to)&&e!=this)
                .forEach(e-> {
                    try {
                        e.session.getBasicRemote()
                                .sendText(FastJsonUtils
                                        .toJSONString(new Server2ClientMessage(message,new Date(),1)));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });

    }
    @OnClose
    public void close(){
        webSocketSet.remove(this);
        onlineSub();
    }

    public synchronized  void onlineAdd(){
        onlineCount++;
    }
    public synchronized void onlineSub(){
        onlineCount--;
    }
}
