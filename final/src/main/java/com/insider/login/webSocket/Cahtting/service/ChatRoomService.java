package com.insider.login.webSocket.Cahtting.service;

import com.insider.login.member.entity.Member;
import com.insider.login.member.repository.MemberRepository;
import com.insider.login.webSocket.Cahtting.Result;
import com.insider.login.webSocket.Cahtting.dto.EntRoomResponseDTO;
import com.insider.login.webSocket.Cahtting.dto.RoomResponseDTO;
import com.insider.login.webSocket.Cahtting.entity.ChatRoom;
import com.insider.login.webSocket.Cahtting.entity.EnteredRoom;
import com.insider.login.webSocket.Cahtting.entity.RoomStatus;
import com.insider.login.webSocket.Cahtting.repository.ChatRoomRepository;
import com.insider.login.webSocket.Cahtting.repository.EnteredRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    private final EnteredRoomRepository enteredRoomRepository;

    private final MemberRepository memberRepository;


    @Transactional
    public Long save(ChatRoom room) {
        return chatRoomRepository.save(room).getRoomId();
    }


    public Result<List<RoomResponseDTO>> findAll(RoomStatus roomStatus) {
        List<ChatRoom> rooms = chatRoomRepository.findAllList(roomStatus);
        List<RoomResponseDTO> collect = rooms.stream().map(RoomResponseDTO::new).collect(Collectors.toList());
        return new Result<>(collect);
    }

    public Result<List<EntRoomResponseDTO>> findAll(int memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            List<EnteredRoom> rooms = enteredRoomRepository.findAll(RoomStatus.ENTER, member);

            List<EntRoomResponseDTO> collect = rooms.stream().map(EntRoomResponseDTO::new).collect(Collectors.toList());

            return new Result<>(collect);
        } else {
            // 해당하는 회원 정보가 없는 경우 처리
            return new Result<>(Collections.emptyList());
        }
    }
}
