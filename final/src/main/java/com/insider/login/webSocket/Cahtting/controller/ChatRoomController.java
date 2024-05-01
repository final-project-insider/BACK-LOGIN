package com.insider.login.webSocket.Cahtting.controller;


import com.insider.login.webSocket.Cahtting.Result;
import com.insider.login.webSocket.Cahtting.dto.EntRoomResponseDTO;
import com.insider.login.webSocket.Cahtting.dto.RoomJoinedReqDTO;
import com.insider.login.webSocket.Cahtting.dto.RoomResponseDTO;
import com.insider.login.webSocket.Cahtting.entity.RoomStatus;
import com.insider.login.webSocket.Cahtting.service.ChatRoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:3000")
@Transactional
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("")
    public Result<List<RoomResponseDTO>> list(RoomStatus roomStatus) {

        System.out.println("쿼리는 잘 출력이 되는데 왜 안나오는거임?");

        return chatRoomService.findAll(roomStatus);

    }

    @GetMapping("/joined")
    public Result<List<EntRoomResponseDTO>> joinedList(RoomJoinedReqDTO requestDTO) {
        return chatRoomService.findAll(requestDTO.getMemberId());
    }

    @PostMapping("/{roomId}/entered")
    public ResponseEntity<String> handleRoomEnteredRequest(@PathVariable("roomId") String roomId) {

        // 이 예시에서는 단순히 요청이 수신되었음을 알리는 메시지를 반환합니다.
        return ResponseEntity.ok("Room entered request for roomId: " + roomId + " received");
    }



}
