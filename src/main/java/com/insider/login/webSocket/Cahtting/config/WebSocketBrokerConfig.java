package com.insider.login.webSocket.Cahtting.config;

import com.insider.login.member.service.MemberService;
import com.insider.login.webSocket.Cahtting.interceptor.WebsocketBrokerInterceptor;
import com.insider.login.webSocket.Cahtting.service.EnteredRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer  {

    private final WebsocketBrokerInterceptor interceptor;

    private final MemberService memberService;


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

//    @EventListener(value = ApplicationReadyEvent.class)
//    public void addTestData() {
//
//        ChatRoom room1 = new ChatRoom("채팅방4");
//        ChatRoom room2 = new ChatRoom("채팅방5");
//
//        Member member1 = new Member();
//        Member member2 = new Member();
//        Member member3 = new Member();
//
//
//
//        Long savedRoomId1 = chatRoomService.save(room1);
//        Long savedRoomId2 = chatRoomService.save(room2);
//
//        EnteredRoom enteredRoom1 = new EnteredRoom();
//        EnteredRoom enteredRoom2 = new EnteredRoom();
//        EnteredRoom enteredRoom3 = new EnteredRoom();
//
//    }


}
