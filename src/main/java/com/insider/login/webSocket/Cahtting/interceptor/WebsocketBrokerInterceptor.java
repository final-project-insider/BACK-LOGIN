package com.insider.login.webSocket.Cahtting.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class WebsocketBrokerInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        final StompCommand commandType = headerAccessor.getCommand();

        //웹소켓 연결 요청 시 유저 인증
        if(StompCommand.CONNECT == commandType) {

        //pub 시 메시지 처리할 경우
        } else if (StompCommand.SEND == commandType) {

        //sub 시 처리할 코드를 여기서 작성
        } else if (StompCommand.SUBSCRIBE == commandType) {
        }
        return message;
    }

}
