package com.insider.login.webSocket.Cahtting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomResponseDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private Long roomId;


}
