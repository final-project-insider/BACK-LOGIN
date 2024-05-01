package com.insider.login.webSocket.Cahtting.config;

import com.insider.login.auth.image.entity.Image;
import com.insider.login.common.utils.MemberRole;
import com.insider.login.department.entity.Department;
import com.insider.login.member.entity.Member;
import com.insider.login.member.service.MemberService;
import com.insider.login.position.entity.Position;
import com.insider.login.webSocket.Cahtting.entity.ChatRoom;
import com.insider.login.webSocket.Cahtting.interceptor.WebsocketBrokerInterceptor;
import com.insider.login.webSocket.Cahtting.service.ChatRoomService;
import com.insider.login.webSocket.Cahtting.service.EnteredRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.time.LocalDate;


@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer  {

    private final WebsocketBrokerInterceptor interceptor;

    private final MemberService memberService;

    private final ChatRoomService chatRoomService;

    private final EnteredRoomService enteredRoomService;



    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/wss/chatting") //1
                .setAllowedOrigins("*");
        System.out.println("웹소켓 잘 들어오는지 체크");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(interceptor); //2
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub"); //3
        registry.setApplicationDestinationPrefixes("/pub"); //4
    }

    @EventListener(value = ApplicationReadyEvent.class)
    public void addTestData() {

        ChatRoom room1 = new ChatRoom("채팅방4");
        ChatRoom room2 = new ChatRoom("채팅방5");


        Long savedRoomId1 = chatRoomService.save(room1);
        Long savedRoomId2 = chatRoomService.save(room2);

    }


}
