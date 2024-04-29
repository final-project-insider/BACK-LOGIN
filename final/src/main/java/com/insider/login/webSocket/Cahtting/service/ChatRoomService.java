package com.insider.login.webSocket.Cahtting.service;

import com.insider.login.member_jee.repository.MemberRepository;
import com.insider.login.webSocket.Cahtting.repository.ChatMessageRepository;
import com.insider.login.webSocket.Cahtting.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;// 채팅방조회

    private final MemberRepository memberRepository;

    private final ChatMessageRepository chatMessageRepository;









}
