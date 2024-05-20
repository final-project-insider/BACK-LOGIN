package com.insider.login.transferredHistory.controller;

import com.insider.login.transferredHistory.service.TransferredHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transferred")
public class TransferredHistoryController {

    private final TransferredHistoryService transferredHistoryService;

    public TransferredHistoryController(TransferredHistoryService transferredHistoryService) {
        this.transferredHistoryService = transferredHistoryService;
    }

    /* 구성원 관리 페이지에서 근속년수 보여주는 logic */
//    @GetMapping("/dateDifference/{transferredNo}")
//    public ResponseEntity<String> getDateDifference(@PathVariable ("transferredNo") int transferredNo) {
//        String dateDifference = transferredHistoryService.calculateDateDifference(transferredNo);       // 날짜의 차이
//        return ResponseEntity.ok(dateDifference);
//    }

}
