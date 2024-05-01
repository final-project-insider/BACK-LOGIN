package com.insider.login.webSocket.Cahtting.repository;

import com.insider.login.webSocket.Cahtting.entity.ChatRoom;
import com.insider.login.webSocket.Cahtting.entity.EnteredRoom;
import com.insider.login.webSocket.Cahtting.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {


    @Query("select distinct r from ChatRoom r join fetch r.enteredRoom e where e.roomStatus = 'ENTER'")
    List<ChatRoom> findAllList(@Param(value = "roomStatus") RoomStatus roomStatus);


}
