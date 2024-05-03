package com.insider.login.leave.service;


import com.insider.login.common.CommonController;
import com.insider.login.leave.dto.LeaveAccrualDTO;
import com.insider.login.leave.dto.LeaveInfoDTO;
import com.insider.login.leave.dto.LeavesDTO;
import com.insider.login.leave.dto.LeaveSubmitDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest
@Transactional
public class LeavesServiceTests extends CommonController {

    @Autowired
    private LeaveService leaveService;

    @Test
    @DisplayName("나의 휴가 신청 내역 조회")
    void testSelectLeaveSubmitListByMemberId() {
        // given
        int applicantId = 241201001;
        //페이징 설정
        int pageNumber = 1;
        String properties = "leaveSubNo";
        String direction = "DESC";
        Pageable pageable;

        if (!direction.equals("DESC")) {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.ASC, properties));
        } else {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, properties));
        }

        // when
        Page<LeaveSubmitDTO> results = leaveService.selectLeaveSubmitList(applicantId, pageable);

        //then
        // 해당 사번으로 조회된 결과가 있을 경우 조회된 내역의 사번이 의도한 사번과 일치해야함
        if (!results.isEmpty()) {
            for (LeaveSubmitDTO dto : results) {
                System.out.println(dto);
                Assertions.assertEquals(dto.getLeaveSubApplicant(), applicantId);
            }
        }
        // 해당 페이지에 표시될 요소의 개수가 10개를 넘으면 안 됨
        Assertions.assertFalse(results.getSize() > 10);
        // 가져온 페이지 중 현재 인덱스가 의도한 페이지와 같아아 함
        Assertions.assertEquals(results.getNumber(), pageNumber);
    }

    @Test
    @DisplayName("개인 휴가 보유내역 조회")
    void testGetLeaveInfoById() {
        // given
        int memberId = 241201001;

        // when
        LeaveInfoDTO info = leaveService.getLeaveInfoById(memberId);

        // then
        // 의도한 사번 아이디와 조회된 DTO의 사번 아이디가 같아야 함
        Assertions.assertEquals(info.getMemberId(), memberId);
        System.out.println(info);
    }

    @Test
    @DisplayName("휴가 신청 내역 조회")
    void testSelectSubmitList() {
        // given
        int applicantId = 0;
        // 페이징 설정
        int pageNumber = 0;
        String properties = "leaveSubNo";
        String direction = "DESC";
        Pageable pageable;

        if (!direction.equals("DESC")) {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.ASC, properties));
        } else {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, properties));
        }

        // then
        Page<LeaveSubmitDTO> results = leaveService.selectLeaveSubmitList(applicantId, pageable);

        // when
        // 해당 페이지에 표시될 요소의 개수가 10개를 넘으면 안 됨
        Assertions.assertFalse(results.getSize() > 10);
        // 가져온 페이지 중 현재 인덱스가 의도한 페이지와 같아아 함
        Assertions.assertEquals(results.getNumber(), pageNumber);
        System.out.println(results.getTotalElements());
        results.forEach(System.out::println);
    }

    @Test
    @DisplayName(("휴가 신청"))
    void testInsertSubmit() {
        // given
        int applicantId = 241201001;
        int leaveSubNo = 5;
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO(applicantId, LocalDate.parse("2024-04-10"), LocalDate.parse("2024-04-11"), "연차", "휴가 상신입니다.");
        leaveSubmitDTO.setRefLeaveSubNo(leaveSubNo);
        leaveSubmitDTO.setLeaveSubApplyDate(nowDate());
        //페이징 설정
        int pageNumber = 0;
        String properties = "leaveSubNo";
        String direction = "DESC";
        Pageable pageable;

        if (!direction.equals("DESC")) {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.ASC, properties));
        } else {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, properties));
        }

        // when
        Page<LeaveSubmitDTO> before = leaveService.selectLeaveSubmitList(leaveSubmitDTO.getLeaveSubApplicant(), pageable);

        String result = leaveService.insertSubmit(leaveSubmitDTO);

        Page<LeaveSubmitDTO> after = leaveService.selectLeaveSubmitList(leaveSubmitDTO.getLeaveSubApplicant(), pageable);

        //then
        // 등록했을 때, 해당 사번으로 조회되는 요소의 개수가 등록 전보다 증가해야함
        Assertions.assertTrue(after.getTotalElements() > before.getTotalElements());
        // 성공 결과 메시지를 반환해야함
        Assertions.assertEquals(result, "신청 등록 성공");

    }

    @Test
    @DisplayName("휴가 신청 취소(삭제)")
    void testDeleteSubmit() {
        // given
        int leaveSubNo = 1;

        // when
        String result = leaveService.deleteSubmit(leaveSubNo);

        // then
        // 성공 결과 메시지를 반환해야함
        Assertions.assertEquals(result, "신청 취소 성공");
        // 해당 신청번호로 조회되는 결과가 없어야함
        Assertions.assertNull(leaveService.selectSubmitByLeaveSubNo(leaveSubNo));
    }

    @Test
    @DisplayName("휴가 취소 요청")
    void testInsertSubmitCancel() {
        // given
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO(1, 202312003, LocalDate.parse("2024-04-10"), LocalDate.parse("2024-04-11"), "휴가 취소 상신입니다.");
        leaveSubmitDTO.setLeaveSubApplyDate(nowDate());
        //페이징 설정
        int pageNumber = 0;
        String properties = "leaveSubNo";
        String direction = "DESC";
        Pageable pageable;

        if (!direction.equals("DESC")) {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.ASC, properties));
        } else {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, properties));

        }

        //when

        Page<LeaveSubmitDTO> before = leaveService.selectLeaveSubmitList(leaveSubmitDTO.getLeaveSubApplicant(), pageable);

        String result = leaveService.insertSubmitCancel(leaveSubmitDTO);

        Page<LeaveSubmitDTO> after = leaveService.selectLeaveSubmitList(leaveSubmitDTO.getLeaveSubApplicant(), pageable);

        //then
        // 등록했을 때, 해당 사번으로 조회되는 요소의 개수가 등록 전보다 증가해야함
        Assertions.assertTrue(after.getTotalElements() > before.getTotalElements());
        // 성공 결과 메시지를 반환해야함
        Assertions.assertEquals(result, "취소요청 등록 성공");
    }

    @Test
    @DisplayName("발생내역조회")
    void testSelectAccrualList() {
        // given
        //페이징 설정
        int pageNumber = 0;
        String properties = "leaveAccrualNo";
        String direction = "DESC";
        Pageable pageable;

        if (!direction.equals("DESC")) {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.ASC, properties));
        } else {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, properties));
        }

        //when
        Page<LeaveAccrualDTO> results = leaveService.selectAccrualList(pageable);

        //then
        // 반환되는 내역이 비어있지 않아야 함
        Assertions.assertFalse(results.isEmpty());
        // 해당 페이지에 표시될 요소의 개수가 10개를 넘으면 안 됨
        Assertions.assertFalse(results.getSize() > 10);
        // 가져온 페이지 중 현재 인덱스가 의도한 페이지와 같아아 함
        Assertions.assertEquals(results.getNumber(), pageNumber);
        results.forEach(System.out::println);

    }

    @Test
    @DisplayName("휴가 발생")
    void testInsertAccrual() {
        // given
        int applicantId = 241201001;
        LeaveAccrualDTO accrualDTO = new LeaveAccrualDTO(applicantId, 3, "예비군");
        int grantorId = 200401023;
        accrualDTO.setGrantorId(grantorId);
        // 페이징 설정
        int pageNumber = 0;
        String properties = "leaveAccrualNo";
        String direction = "DESC";
        Pageable pageable;

        if (!direction.equals("DESC")) {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.ASC, properties));
        } else {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, properties));
        }

        //when
        Page<LeaveAccrualDTO> before = leaveService.selectAccrualList(pageable);

        String result = leaveService.insertAccrual(accrualDTO);

        Page<LeaveAccrualDTO> after = leaveService.selectAccrualList(pageable);

        //then
        // 발생에 성공하면 발생내역의 요소 수가 발생 이전보다 증가해야 함
        Assertions.assertTrue(after.getTotalElements() > before.getTotalElements());
        // 성공 메시지를 반환해야 함
        Assertions.assertEquals(result, "휴가발생 등록 성공");

    }

    @Test
    @DisplayName("상세조회")
    void testSelectSubmitByLeaveSubNo() {
        // given
        int leaveSubNo = 7;

        // when
        LeaveSubmitDTO result = leaveService.selectSubmitByLeaveSubNo(leaveSubNo);

        // then
        // 조회 결과가 있어야 함
        Assertions.assertNotNull(result);
        // 조회해온 결과의 신청번호가 의도한 것과 같아야 함
        Assertions.assertEquals(result.getLeaveSubNo(), leaveSubNo);

    }

    @Test
    @DisplayName("휴가 신청 처리 - 승인")
    void testUpdateSubimtOk() {
        // given
        int leaveSubNo = 6;
        String decision = "승인";
        int approverId = 200401023;
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO(leaveSubNo, approverId, decision, nowDate());

        // when
        String result = leaveService.updateSubmit(leaveSubmitDTO);

        // then
        // 성공메시지를 반환해야함
//        Assertions.assertEquals(result, "휴가처리 성공");
        // 업데이트가 의도한 대로 진행됐는지 확인
        LeaveSubmitDTO test = leaveService.selectSubmitByLeaveSubNo(leaveSubNo);
        System.out.println(test);
        Assertions.assertEquals(test.getLeaveSubApprover(), approverId);
        Assertions.assertEquals(test.getLeaveSubStatus(), decision);

    }

    @Test
    @DisplayName("휴가 신청 처리 - 반려")
    void testUpdateSubimtNo() {
        // given
        int leaveSubNo = 9;
        String decision = "반려";
        int approverId = 200401023;
        String reason = "반려사유";
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO(leaveSubNo, approverId, decision, nowDate(), reason);

        // when
        String result = leaveService.updateSubmit(leaveSubmitDTO);

        // then
        // 성공메시지를 반환해야함
//        Assertions.assertEquals(result, "휴가처리 성공");
        // 업데이트가 의도한 대로 진행됐는지 확인
        LeaveSubmitDTO test = leaveService.selectSubmitByLeaveSubNo(leaveSubNo);
        System.out.println(test);
        Assertions.assertEquals(test.getLeaveSubApprover(), approverId);
        Assertions.assertEquals(test.getLeaveSubStatus(), decision);

    }


    @Test
    @DisplayName("휴가 보유 내역 조회")
    void testSelectLeavesList() {
        // given
        // 페이징 설정
        int pageNumber = 0;
        String properties = "leaveNo";
        String direction = "DESC";
        Pageable pageable;

        if (direction != "DESC") {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.ASC, properties));
        } else {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, properties));
        }

        // when
        Page<LeaveInfoDTO> results = leaveService.selectLeavesList(pageable);

        // then
        // 해당 페이지에 표시될 요소의 개수가 10개를 넘으면 안 됨
        Assertions.assertFalse(results.getSize() > 10);
        // 가져온 페이지 중 현재 인덱스가 의도한 페이지와 같아아 함
        Assertions.assertEquals(results.getNumber(), pageNumber);
        results.forEach(System.out::println);

    }

}
