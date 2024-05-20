package com.insider.login.webSocket.Cahtting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntRoomResponseDTO {

    @JsonProperty("id")
    private long roomId;

    @JsonProperty("name")
    private String roomName;


}
