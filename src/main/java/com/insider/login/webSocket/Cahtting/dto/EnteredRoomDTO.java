package com.insider.login.webSocket.Cahtting.dto;

import com.insider.login.webSocket.Cahtting.entity.ChatRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EnteredRoomDTO {

    private int memberId;

    public EnteredRoomDTO(ChatRoom enteredRoom) {
        this.memberId = enteredRoom.getMember().getMemberId();
    }

}
