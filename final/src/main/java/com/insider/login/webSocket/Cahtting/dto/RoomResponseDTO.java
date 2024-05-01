package com.insider.login.webSocket.Cahtting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insider.login.webSocket.Cahtting.entity.ChatRoom;

public class RoomResponseDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private Long roomId;

    public RoomResponseDTO(ChatRoom room) {
        this.name = room.getName();
        this.roomId = room.getRoomId();
    }

}
