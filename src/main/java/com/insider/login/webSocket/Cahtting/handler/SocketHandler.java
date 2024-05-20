package com.insider.login.webSocket.Cahtting.handler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class SocketHandler extends TextWebSocketHandler {

    // WebSocket 클라이언트를 추적하기 위한, 해시맵 선언
    // String 클라이언트 식별자 key / session 객체를 값으로 사용
    // CLIENTS 변수에 세션을 담아두기 위한 맵
    private static final Map<String, WebSocketSession> CLIENTS = new HashMap<>();


    //최초 연결 시
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        final String sessionId = session.getId();
        final String enteredMessage = sessionId + "님이 입장하셨습니다";

        CLIENTS.put(sessionId, session);

        sendMessage(sessionId, new TextMessage(enteredMessage));


    }

    //양방향 데이터 통신할 떄 해당 메서드가 call 된다.
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        final String sessionId = session.getId();
        CLIENTS.values().forEach((client) -> {
            try {
                client.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    //웹소켓 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        final String sessionId = session.getId();
        final String leaveMessage = sessionId + "님이 떠났습니다.";
        CLIENTS.remove(sessionId); // 삭제

        sendMessage(sessionId, new TextMessage(leaveMessage));
    }

    //통신 에러 발생 시
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {}

    // 세션 id로 모든 세션에 메시지를 보내는 기능 -> 공통화
    private void sendMessage(String sessionId, WebSocketMessage<?> message) {
        CLIENTS.values().forEach(s -> {
            if(!s.getId().equals(sessionId) && s.isOpen()) {
                try {
                    s.sendMessage(message);
                } catch (IOException e) {}
            }
        });
    }


}
