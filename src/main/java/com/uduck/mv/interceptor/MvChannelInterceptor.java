package com.uduck.mv.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.concurrent.CopyOnWriteArraySet;

public class MvChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private CopyOnWriteArraySet<String> onlineNumSet = new CopyOnWriteArraySet<>();

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        return message;
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        String sessionId = accessor.getSessionId();
        String destination = accessor.getDestination();

        System.out.println(destination);

        if (StompCommand.SUBSCRIBE.equals(command)){
            onlineNumSet.add(sessionId);
            simpMessagingTemplate.convertAndSend("/topic/online",String.valueOf(onlineNumSet.size()));
        }

        if (StompCommand.DISCONNECT.equals(command)){
            onlineNumSet.remove(sessionId);
            simpMessagingTemplate.convertAndSend("/topic/online",String.valueOf(onlineNumSet.size()));
        }
    }
}
