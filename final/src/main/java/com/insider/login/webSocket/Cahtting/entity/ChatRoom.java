package com.insider.login.webSocket.Cahtting.entity;

import com.insider.login.member_jee.entity.Member;
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
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "Entered_room")
    private List<EnteredRoom> enteredRoom = new ArrayList<>();

    public ChatRoom(String name) {
        this.name = name;
    }

}
