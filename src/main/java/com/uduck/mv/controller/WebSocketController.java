package com.uduck.mv.controller;

import com.uduck.mv.entity.form.VMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/danmu")
    @SendTo("/topic/common_message")
    public VMessage receiveMsg(VMessage message){
        System.out.println("test: " + message.getEmitContent());
        return message;
    }
}
