package com.insider.login.webSocket.Cahtting.dto;

import com.insider.login.webSocket.Cahtting.entity.EnteredRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EnteredRoomDTO {

    private int memberId;

    public EnteredRoomDTO(EnteredRoom enteredRoom) {
        this.memberId = enteredRoom.getMember().getMemberId();
    }

}
