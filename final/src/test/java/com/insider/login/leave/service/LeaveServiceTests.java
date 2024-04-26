package com.insider.login.leave.service;


import com.insider.login.common.CommonController;
import com.insider.login.leave.dto.LeaveSubmitDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Date;
import java.util.List;

@SpringBootTest
@Transactional
public class LeaveServiceTests extends CommonController {

    @Autowired
    private LeaveService leaveService;

    @BeforeEach
    public void setUp() {
        LeaveSubmitDTO leaveSubmitDTO1 = new LeaveSubmitDTO(202312003, Date.valueOf("2024-04-10"), Date.valueOf("2024-04-11"), "연차", "휴가 상신입니다.");
        LeaveSubmitDTO leaveSubmitDTO2 = new LeaveSubmitDTO(202214001, Date.valueOf("2024-04-15"), Date.valueOf("2024-04-16"), "오전반차", "휴가 상신입니다.");
        LeaveSubmitDTO leaveSubmitDTO3 = new LeaveSubmitDTO(202130001, Date.valueOf("2024-04-17"), Date.valueOf("2024-04-18"), "오후반차", "휴가 상신입니다.");
        LeaveSubmitDTO leaveSubmitDTO4 = new LeaveSubmitDTO(201923001, Date.valueOf("2024-04-20"), Date.valueOf("2024-04-21"), "특별휴가", "휴가 상신입니다.");
        leaveSubmitDTO1.setLeaveSubApplyDate(nowDate());
        leaveSubmitDTO2.setLeaveSubApplyDate(nowDate());
        leaveSubmitDTO3.setLeaveSubApplyDate(nowDate());
        leaveSubmitDTO4.setLeaveSubApplyDate(nowDate());
        leaveService.insertSubmit(leaveSubmitDTO1);
        leaveService.insertSubmit(leaveSubmitDTO2);
        leaveService.insertSubmit(leaveSubmitDTO3);
        leaveService.insertSubmit(leaveSubmitDTO4);
    }

    @Test
    @DisplayName("휴가 신청 내역 조회")
    void testSelectLeaveSubmitListByMemberId() {
        // given
        int applicantId = 202312003;

        // when
        List<LeaveSubmitDTO> results = leaveService.selectLeaveSubmitListByMemberId(applicantId);

        //then
        Assertions.assertFalse(results.isEmpty());
        results.forEach(System.out::println);
    }

    @Test
    @DisplayName(("휴가 신청"))
    void testInsertSubmit() {
        // given
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO(202312003, Date.valueOf("2024-04-10"), Date.valueOf("2024-04-11"), "연차", "휴가 상신입니다.");
        leaveSubmitDTO.setLeaveSubApplyDate(nowDate());
        // when
        String result = leaveService.insertSubmit(leaveSubmitDTO);

        //then
        Assertions.assertFalse(leaveService.selectLeaveSubmitListByMemberId(leaveSubmitDTO.getLeaveSubApplicant()).isEmpty());
        leaveService.selectLeaveSubmitListByMemberId(leaveSubmitDTO.getLeaveSubApplicant()).forEach(System.out::println);
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
        Assertions.assertEquals(result, "신청 취소 성공");
        Assertions.assertNull(leaveService.selectSubmitByLeaveSubNo(leaveSubNo));
    }

    @Test
    @DisplayName("휴가 취소 요청")
    void testInsertSubmitCancel() {
        // given
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO(1, 202312003, Date.valueOf("2024-04-10"), Date.valueOf("2024-04-11"), "휴가 취소 상신입니다.");
        leaveSubmitDTO.setLeaveSubApplyDate(nowDate());

        //when
        String result = leaveService.insertSubmitCancel(leaveSubmitDTO);

        //then
        Assertions.assertEquals(result, "취소요청 등록 성공");
    }

    @Test
    @DisplayName("발생내역조회")
    void testSelectAccrualList() {

    }

    @Test
    @DisplayName("휴가 발생")
    void testInsertAccrual() {

    }

    @Test
    @DisplayName("상세조회")
    void testSelectSubmitByLeaveSubNo() {
        // given
        System.out.println(leaveService.selectLeaveSubmitListByMemberId(202312003));
        int leaveSubNo = 3;

        // when
        LeaveSubmitDTO result = leaveService.selectSubmitByLeaveSubNo(leaveSubNo);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getLeaveSubNo(), 3);

    }

}
