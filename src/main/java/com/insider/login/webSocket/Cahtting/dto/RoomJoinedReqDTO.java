package com.insider.login.webSocket.Cahtting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomJoinedReqDTO {


    private int memberId;

    public RoomJoinedReqDTO(int memberId) {
        this.memberId  = memberId;
    }

}
