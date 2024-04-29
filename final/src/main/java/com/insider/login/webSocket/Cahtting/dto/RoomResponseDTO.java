package com.insider.login.webSocket.Cahtting.dto;

import com.insider.login.webSocket.Cahtting.entity.ChatRoom;

public class RoomResponseDTO {

    private String name;

    private Long roomId;

    public RoomResponseDTO(ChatRoom room) {
        this.name = room.getName();
        this.roomId = room.getId();
    }

}
