package com.insider.login.webSocket.Cahtting.repository;

import com.insider.login.webSocket.Cahtting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
