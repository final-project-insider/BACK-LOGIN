package com.insider.login.other.notice.service;

import com.insider.login.other.notice.dto.NoticeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class NoticeServiceTests {

    @Autowired
    private NoticeService noticeService;

    @DisplayName("출퇴근 정정 요청 알림 테스트")
    @Test
    void testInsertNoticeOfReqForCorrect() {
        //given
        int memberId = 2024001003;
        LocalDateTime noticeDateTime = LocalDateTime.now();
        String noticeType = "출퇴근";
        String noticeContent = "새로운 출퇴근 정정 요청이 있습니다. 확인해주세요.";

        NoticeDTO newNoticeOfReqForCorrect = new NoticeDTO(
                memberId,
                noticeDateTime,
                noticeType,
                noticeContent
        );


        //when

        //then
        Assertions.assertDoesNotThrow(
                () -> noticeService.insertNewNotice(newNoticeOfReqForCorrect));
    }

    @DisplayName("출퇴근 정정 처리 알림 테스트")
    @Test
    void testInsertNoticeOfProcessForCorrect() {
        /** 출퇴근 정정 처리 알림 시 정정 요청한 멤버에게 등록 해야함 */
        //given
        int memberId = 2024001003;
        LocalDateTime noticeDateTime = LocalDateTime.now();
        String noticeType = "출퇴근";
        String noticeContent = "출퇴근 정정 요청이 처리되었습니다. 확인해주세요.";

        NoticeDTO newNoticeOfProcessForCorrect = new NoticeDTO(
                memberId,
                noticeDateTime,
                noticeType,
                noticeContent
        );

        //when

        //then
        Assertions.assertDoesNotThrow(
                () -> noticeService.insertNewNotice(newNoticeOfProcessForCorrect));
    }

    @DisplayName("전자결재 상신 알림 테스트")
    @Test
    void testInsertNoticeOfApproval() {
        /** 전자결재 상신 알림 시 상신자에게 등록 해야함 */
        //given
        int memberId = 2024001001;
        LocalDateTime noticeDateTime = LocalDateTime.now();
        String noticeType = "전자결재";
        String noticeContent = "새로운 결재 기안이 있습니다. 확인해주세요.";

        NoticeDTO newNoticeOfApproval = new NoticeDTO(
                memberId,
                noticeDateTime,
                noticeType,
                noticeContent
        );

        //when

        //then
        Assertions.assertDoesNotThrow(
                () -> noticeService.insertNewNotice(newNoticeOfApproval));
    }

    @DisplayName("전자결재 결재 알림 테스트")
    @Test
    void testInsertNoticeOfResultForApproval() {
        /** 전자결재 결재 알림 시 기안자에게 등록 해야함 */
        //given
        int memberId = 2024001001;
        LocalDateTime noticeDateTime = LocalDateTime.now();
        String noticeType = "전자결재";
        String noticeContent = "기안하신 결재가 처리되었습니다. 확인해주세요.";

        NoticeDTO newNoticeOfResultForApproval = new NoticeDTO(
                memberId,
                noticeDateTime,
                noticeType,
                noticeContent
        );

        //when

        //then
        Assertions.assertDoesNotThrow(
                () -> noticeService.insertNewNotice(newNoticeOfResultForApproval));
    }

    @DisplayName("수신한 알림 내역 조회 테스트")
    @Test
    void testSelectNoticeListByMemberId() {
        //given
        int memberId = 2024001001;

        //when
        Map<String, Object> result = new HashMap<>();
        result = noticeService.selectNoticeListByMemberId(memberId);

        //then
        Assertions.assertNotNull(result);
//        result.forEach(notice -> System.out.println("notice : " + notice));
    }

    @DisplayName("알림 선택 삭제 테스트")
    @Test
    void testDeleteNoticeByNoticeNo() {
        //given
        int noticeNo = 1;

        //when

        //then
        Assertions.assertDoesNotThrow(
                () -> noticeService.deleteNoticeByNoticeNo(noticeNo));
    }

    @DisplayName("알림 전체 삭제 테스트")
    @Test
    void testDeleteNoticeListByMemberId() {
        //given
        int memberId = 2024001001;

        //when

        //then
        Assertions.assertDoesNotThrow(
                () -> noticeService.deleteNoticeListByMemberId(memberId));
    }
}
