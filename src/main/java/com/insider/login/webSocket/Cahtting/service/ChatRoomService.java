package com.insider.login.webSocket.Cahtting.service;

import com.insider.login.member.entity.Member;
import com.insider.login.member.repository.MemberRepository;
import com.insider.login.webSocket.Cahtting.dto.EntRoomReqDTO;
import com.insider.login.webSocket.Cahtting.entity.ChatRoom;
import com.insider.login.webSocket.Cahtting.entity.RoomStatus;
import com.insider.login.webSocket.Cahtting.repository.EnteredRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final EnteredRoomRepository enteredRoomRepository;

    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;

    /** 방 생성 */
    public ChatRoom save(int memberId, int receiverMemberId, String roomName) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Member receiverMember = memberRepository.findById(receiverMemberId).orElse(null);

        if (member == null || receiverMember == null ) {
            System.out.println("실패 했음");
        }
        String enteredRoomName = roomName;
        ChatRoom enteredRoom = new ChatRoom(member, receiverMember, enteredRoomName);
        enteredRoomRepository.save(enteredRoom);
        return enteredRoom;

    }

    /** 방 조회 */
//    public List<EntRoomReqDTO> selectRoomList() {
//
//        List<EnteredRoom> rooms = enteredRoomRepository.findAllList();
//
//        List<EntRoomReqDTO> roomList = new ArrayList<>();
//
//        for (EnteredRoom room : rooms) {
//            EntRoomReqDTO enteredRoom = modelMapper.map(room, EntRoomReqDTO.class);
//            roomList.add(enteredRoom);
//        }
//
//        return roomList;
//
//    }
    public List<EntRoomReqDTO> selectRoomList(Optional<Member> member) {


        List<ChatRoom> rooms = enteredRoomRepository.findAllList(RoomStatus.ENTER, member);

        List<EntRoomReqDTO> roomList = new ArrayList<>();

        for (ChatRoom room : rooms) {
            EntRoomReqDTO enteredRoom = modelMapper.map(room, EntRoomReqDTO.class);
            roomList.add(enteredRoom);
        }

        return roomList;
    }

    /** 방 삭제 */
    public ChatRoom deleteRoom(int enteredRoomId) {
        ChatRoom enteredRoom = enteredRoomRepository.findById(enteredRoomId).orElse(null);

        if (enteredRoom != null) {
            enteredRoomRepository.delete(enteredRoom);
        }
        return enteredRoom;
    }

}
