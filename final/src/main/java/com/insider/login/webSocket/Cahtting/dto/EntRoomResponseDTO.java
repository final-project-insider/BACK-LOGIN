package com.insider.login.webSocket.Cahtting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insider.login.webSocket.Cahtting.entity.EnteredRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntRoomResponseDTO {

    @JsonProperty("id")
    private long roomId;

    @JsonProperty("name")
    private String roomName;



    public EntRoomResponseDTO(EnteredRoom enteredRoom) {
        this.roomName = enteredRoom.getChatRoom().getName();
        this.roomId = enteredRoom.getChatRoom().getRoomId();
    }

}
