package com.insider.login.webSocket.Cahtting.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessageDTO implements java.io.Serializable {

    private String message;

    private String writer;



}
