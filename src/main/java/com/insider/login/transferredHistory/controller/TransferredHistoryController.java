package com.insider.login.transferredHistory.controller;

import com.insider.login.transferredHistory.dto.TransferredHistoryDTO;
import com.insider.login.transferredHistory.entity.TransferredHistory;
import com.insider.login.transferredHistory.service.TransferredHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
public class TransferredHistoryController {

    private final TransferredHistoryService transferredHistoryService;


    public TransferredHistoryController(TransferredHistoryService transferredHistoryService) {
        this.transferredHistoryService = transferredHistoryService;
    }

    /* 구성원 관리 페이지에서 근속년수 보여주는 logic */
    @GetMapping("/dateDifference/{transferredNo}")
    public ResponseEntity<String> getDateDifference(@PathVariable ("transferredNo") int transferredNo) {
        String dateDifference = transferredHistoryService.calculateDateDifference(transferredNo);       // 날짜의 차이
        return ResponseEntity.ok(dateDifference);
    }

    @GetMapping("/transferredHistory/{memberId}")
    public ResponseEntity<TransferredHistoryDTO> getTransferredHistoryRecord(@PathVariable ("memberId") String receivedMemberId) {
        try {
            int memberId = Integer.parseInt(receivedMemberId);
            TransferredHistoryDTO transferredHistoryDTO = transferredHistoryService.getTransferredHistoryRecord(memberId);
            return ResponseEntity.ok().body(transferredHistoryDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
