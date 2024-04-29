package com.insider.login.webSocket.Cahtting.controller;

import com.insider.login.member_jee.repository.MemberRepository;
import com.insider.login.webSocket.Cahtting.repository.ChatRoomRepository;
import com.insider.login.webSocket.Cahtting.service.ChatMessageService;
import com.insider.login.webSocket.Cahtting.service.ChatRoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting")
@Transactional
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageService chatMessageService;

    private final MemberRepository memberRepository;



}
