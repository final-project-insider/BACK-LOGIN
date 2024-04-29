package com.insider.login.webSocket.Cahtting.dto;

import com.insider.login.webSocket.Cahtting.entity.EnteredRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntRoomResponseDTO {

    private long roomId;

    private String roomName;

    public EntRoomResponseDTO(EnteredRoom enteredRoom) {
        this.roomName = enteredRoom.getChatRoom().getName();
        this.roomId = enteredRoom.getChatRoom().getId();
    }

}
