package com.insider.login.webSocket.Cahtting.entity;

import com.insider.login.member_jee.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class EnteredRoom {

    @Id
    @GeneratedValue
    private int enteredRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID")
    private ChatRoom chatRoom;

    @Enumerated(value = EnumType.STRING)
    private RoomStatus roomStatus = RoomStatus.ENTER;

    public EnteredRoom(Member member, ChatRoom room) {
        this.member = member;
        this.chatRoom = room;
    }



}
