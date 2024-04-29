package com.insider.login.webSocket.Cahtting.dto;

import com.insider.login.webSocket.Cahtting.entity.EnteredRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EntRoomDTO {

    private String username;

    public EntRoomDTO(EnteredRoom enteredRoom) {
        this.username = enteredRoom.getMember().getName();
    }

}
