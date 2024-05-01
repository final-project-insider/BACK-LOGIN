package com.insider.login.webSocket.Cahtting.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "chat_room")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "chatRoom")
    private List<EnteredRoom> enteredRoom = new ArrayList<>();

    public ChatRoom(String name) {
        this.name = name;
    }

}
