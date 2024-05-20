package com.insider.login.webSocket.Cahtting.controller;


import com.insider.login.member.dto.MemberDTO;
import com.insider.login.member.entity.Member;
import com.insider.login.member.repository.MemberRepository;
import com.insider.login.member.service.MemberService;
import com.insider.login.webSocket.Cahtting.dto.EntRoomReqDTO;
import com.insider.login.webSocket.Cahtting.entity.ChatRoom;
import com.insider.login.webSocket.Cahtting.service.ChatRoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:3000")
@Transactional
public class ChatRoomController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final ChatRoomService enteredRoomService;



    @GetMapping("/members")
    public List<MemberDTO> allMmemberList() {

        return memberService.selectMemberList();
    }

    @GetMapping("/")
    public List<EntRoomReqDTO> selectRoomList(@RequestParam("memberId") int memberId) {

        Optional<Member> member = memberRepository.findById(memberId);
        return enteredRoomService.selectRoomList(member);
    }



    /** 방 만들기 */
    @PostMapping("/")
    public ResponseEntity<ChatRoom> saveEnteredRoom(@RequestBody EntRoomReqDTO request) {
        int memberId = request.getMemberId();
        int receiverId = request.getReceiverId();
        String roomName = request.getRoomName();

        ChatRoom enteredRoom = enteredRoomService.save(memberId, receiverId, roomName);

        if (enteredRoom == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok(enteredRoom);
        }
    }

    /** 방 삭제 */
    @DeleteMapping("/{enteredRoomId}")
    public ResponseEntity<ChatRoom> deleteEnteredRoom(@PathVariable("enteredRoomId") int enteredRoomId) {

        System.out.println("❤️" + enteredRoomId);
        ChatRoom enteredRoom =  enteredRoomService.deleteRoom(enteredRoomId);
        if (enteredRoom == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok(enteredRoom);
        }

    }






}
