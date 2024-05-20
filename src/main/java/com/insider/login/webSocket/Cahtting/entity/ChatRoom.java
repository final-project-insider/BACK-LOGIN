package com.insider.login.webSocket.Cahtting.entity;

import com.insider.login.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "entered_room")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entered_room_id")
    private int enteredRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_MEMBER", referencedColumnName = "MEMBER_ID")
    private Member receiverMember;

    @Column(name = "room_name")
    private String roomName;


    @Enumerated(value = EnumType.STRING)
    private RoomStatus roomStatus = RoomStatus.ENTER;


    public ChatRoom(Member member, Member receiverMember, String roomName) {
        this.member = member;
        this.receiverMember = receiverMember;
        this.roomName = roomName;
    }



}
