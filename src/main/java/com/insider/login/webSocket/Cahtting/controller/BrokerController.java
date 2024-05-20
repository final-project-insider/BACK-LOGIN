package com.insider.login.webSocket.Cahtting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insider.login.webSocket.Cahtting.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class
BrokerController {

    private final SimpMessagingTemplate template;


    /* 접속 후 메시지 전송 */
    @MessageMapping("/room/{roomId}")
    public void sendMessage(@DestinationVariable(value = "roomId") String roomId, MessageDTO message) {

        try {
            log.info("# roomId = {}", roomId);
            log.info("# message = {}", message.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            String payload = objectMapper.writeValueAsString(message);
            template.convertAndSend("/sub/room/" + roomId, payload);
        } catch (Exception e) {
            log.error("Error processing message", e);
        }
    }


    /* 유저가 채팅방을 클릭해서 들어오는 경우 */
    @MessageMapping("/room/{roomId}/entered")
    public void entered(@DestinationVariable(value = "roomId") String roomId, MessageDTO message){

        try {
            log.info("# roomId = {}", roomId);
            log.info("# message = {}", message.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            String payload = objectMapper.writeValueAsString(message);
            template.convertAndSend("/sub/room/" + roomId, payload);
        } catch (Exception e) {
            log.error("Error processing message", e);
        }


    }

    @MessageMapping("/room/{roomId}/leave")
    public void leave(@DestinationVariable(value = "roomId") String roomId, MessageDTO message) {
        try {
            log.info("# leave method");
            ObjectMapper objectMapper = new ObjectMapper();
            String payload = objectMapper.writeValueAsString(message);
            template.convertAndSend("/sub/room/" + roomId, payload);
        } catch (Exception e) {
            log.error("Error processing message", e);
        }
    }


}
