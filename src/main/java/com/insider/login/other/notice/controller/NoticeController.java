package com.insider.login.other.notice.controller;

import com.insider.login.common.ResponseMessage;
import com.insider.login.other.notice.dto.NoticeDTO;
import com.insider.login.other.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    /** 새로운 알림 수신 (등록) 테스트 */
    @PostMapping("/notices")
    public ResponseEntity<ResponseMessage> insertNoticeOfReqForCorrect(@RequestBody NoticeDTO newNotice) {
        return ResponseEntity.ok().body(new ResponseMessage(200, "등록 성공", noticeService.insertNewNotice(newNotice)));
    }

    /** 수신한 알림 내역 조회 테스트 */
    @GetMapping("/notices")
    public ResponseEntity<ResponseMessage> selectNoticeListByMemberId(@RequestParam(value = "memberId") int memberId) {
        return ResponseEntity.ok().body(new ResponseMessage(200, "조회 성공", noticeService.selectNoticeListByMemberId(memberId)));
    }

    /** 알림 선택 삭제 테스트 */
    @DeleteMapping("/notices/{noticeNo}")
    public ResponseEntity<ResponseMessage> deleteNoticeByNoticeNo(@PathVariable(value = "noticeNo") int noticeNo) {
        return ResponseEntity.ok().body(new ResponseMessage(200, "선택 삭제 성공", noticeService.deleteNoticeByNoticeNo(noticeNo)));
    }

    /** 알림 전체 삭제 테스트 */
    @DeleteMapping("/members/{memberId}/notices")
    public ResponseEntity<ResponseMessage> deleteNoticeListByMemberId(@PathVariable(value = "memberId") int memberId) {
        return ResponseEntity.ok().body(new ResponseMessage(200, "전체 삭제 성공", noticeService.deleteNoticeListByMemberId(memberId)));
    }

}
