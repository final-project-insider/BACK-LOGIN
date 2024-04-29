package com.insider.login.webSocket.Cahtting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {


    /* 쿼리메서드 */
    ChatMessage findTopByRoomNumberOrderByCreatedDateDesc(int roomNumber);
}
