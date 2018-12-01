package com.uduck.mv.interceptor;

import com.uduck.mv.repository.OnlineNumRepository;
import com.uduck.mv.repository.PlayNumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class MvChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private OnlineNumRepository onlineNumRepository;

    @Autowired
    private PlayNumRepository playNumRepository;

    private Map<String, String> session2destination = new HashMap<>();

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

        if (StompCommand.SUBSCRIBE.equals(command)){
            System.out.println("******************* subscribe --- destination:"+destination);
            if (destination != null && destination.contains("/topic/online/")){
                session2destination.put(sessionId, destination);
                onlineNumRepository.addOnlineNum(destination, sessionId);
                playNumRepository.addNum(destination, sessionId);
            } else {
                String newDestination = session2destination.get(sessionId);
                if (newDestination != null){
                    simpMessagingTemplate.convertAndSend(newDestination,String.valueOf(onlineNumRepository.getOnlineNumByKey(newDestination)));
                }
            }
        }

        if (StompCommand.DISCONNECT.equals(command)){
            System.out.println("******************* disconnect --- destination:"+destination);
            //onlineNumSet.remove(sessionId);
            String desc = session2destination.remove(sessionId);
            onlineNumRepository.subOnlineNum(desc, sessionId);
            simpMessagingTemplate.convertAndSend(desc,String.valueOf(onlineNumRepository.getOnlineNumByKey(desc)));
        }
    }
}
