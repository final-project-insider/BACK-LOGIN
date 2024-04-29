package com.insider.login.webSocket.Cahtting.config;

import com.insider.login.webSocket.Cahtting.interceptor.WebsocketBrokerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/init") //1
                .setAllowedOrigins("*");
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


}
