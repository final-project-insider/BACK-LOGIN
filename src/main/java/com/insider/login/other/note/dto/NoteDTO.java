package com.insider.login.other.note.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoteDTO implements java.io.Serializable {

    private int noteNo;

    private String sendNoteDate;

    private String noteTitle;

    private String noteContent;

    private int senderId;    // 사번

    private int receiverId;  // 사번

    private String sendDeleteYn;    // 삭제여부

    private String receiveDeleteYn;;


}
