package com.insider.login.webSocket.Cahtting.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insider.login.webSocket.Cahtting.dto.MessageDTO;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BrokerController {

    private final SimpMessagingTemplate template;

    @MessageMapping("/test")
    public void test(SimpMessageHeaderAccessor accessor) {
        log.info("### test method {}", accessor);
    }


    /* 접속 후 메시지 전송 */
    @MessageMapping("/room/{roomId}")
    @SendTo
    public void sendMessage(@DestinationVariable(value = "roomId") String roomId, MessageDTO message) {
        log.info("# roomId = {}", roomId);
        log.info("# message = {}", message);

        template.convertAndSend("/room/" + roomId, message.getMessage());
    }


    /* 유저가 채팅방을 클릭해서 들어오는 경우 */
    @MessageMapping("/room/{roomId}/entered")
    public void entered(@DestinationVariable(value = "roomId") String roomId, MessageDTO message){

        log.info("# roomId = {}", roomId);
        log.info("# message = {}", message);
        final String payload = message.getWriter() + "님이 입장하셨습니다.";
        template.convertAndSend("/sub/room/" + roomId, payload);

    }

    @MessageMapping("/room/{roomId}/leave")
    public void leave(@DestinationVariable(value = "roomId") String roomId, MessageDTO message) {
        try {

            log.info("# leave method");
            String payload = message.getWriter();
            template.convertAndSend("/sub/room/" + roomId, payload);
        } catch (Exception e) {
            log.error("Error processing message", e);
        }
    }


}
