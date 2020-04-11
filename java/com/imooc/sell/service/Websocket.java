package com.imooc.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @program: sell
 * @description: websocket实现
 * @author: flj
 * @create: 2020-03-02 09:03
 **/
@Component
@ServerEndpoint("/websocket")
@Slf4j
public class Websocket {

    private Session session;

    private static CopyOnWriteArrayList<Websocket> web = new CopyOnWriteArrayList<>();

    @OnOpen
    public void open(Session session){
        this.session = session;
        web.add(this);
        log.info("【websocket连接成功】 总数:{}",web.size());
    }

    @OnClose
    public void close(){
        web.remove(this);
        log.info("【websocket连接失败】 总数:{}",web.size());
    }

    @OnMessage
    public void onMessage(String message){
        log.info("【websocket收到消息】 消息内容:{}",message);
    }

    public void sendMessage(String message){
        for(Websocket websocket:web){
            log.info("【websocket广播发送消息】 消息内容:{}",message);
            try {
                websocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
