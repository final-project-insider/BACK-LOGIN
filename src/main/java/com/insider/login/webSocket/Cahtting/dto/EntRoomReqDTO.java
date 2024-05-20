package com.insider.login.webSocket.Cahtting.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EntRoomReqDTO {

        private int memberId;

        private int enteredRoomId;

        private int receiverId;

        private String roomName;


}
