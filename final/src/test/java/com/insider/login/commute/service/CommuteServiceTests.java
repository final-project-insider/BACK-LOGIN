package com.insider.login.commute.service;

import com.insider.login.commute.dto.CommuteDTO;
import com.insider.login.commute.dto.CorrectionDTO;
import com.insider.login.commute.dto.UpdateProcessForCorrectionDTO;
import com.insider.login.commute.dto.UpdateTimeOfCommuteDTO;
import com.insider.login.commute.entity.Commute;
import com.insider.login.member.entity.Department;
import com.insider.login.member.entity.Member;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootTest
public class CommuteServiceTests {

    @Autowired
    private CommuteService commuteService;

    private static Stream<Arguments> getStartWork() {
        return Stream.of(
                Arguments.of(
                        2024001003,
                        LocalDate.now(),
                        LocalTime.of(8,55),
                        null,
                        "근무중",
                        0
                )
        );
    }

    @DisplayName("출근 시간 등록 테스트")
    @ParameterizedTest
    @MethodSource("getStartWork")
//    @Transactional
    void testInsertTimeOfCommute(int memberId, LocalDate workingDate, LocalTime startWork, LocalTime endWork,
                                 String workingStatus, Integer totalWorkingHours) {
        //given
        CommuteDTO newCommute = new CommuteDTO(
                memberId,
                workingDate,
                startWork,
                endWork,
                workingStatus,
                totalWorkingHours
        );

        //when

        //then
        Assertions.assertDoesNotThrow(
                () -> commuteService.insertTimeOfCommute(newCommute)
        );
    }

    @DisplayName("퇴근 시간 등록 테스트")
    @Test
//    @Transactional
    void testUpdateTimeOfCommuteByCommuteNo() {
        //given
        int commuteNo = 10;
        LocalTime endWork = LocalTime.of(18,00);
        String workingStatus = "퇴근";

        /** 총 근무 시간 %%% 분 형식 (int)으로 만들기 */
        Duration workingDuration = Duration.between(LocalTime.of(8,55), endWork).minusHours(1);
        int totalWorkingHours = (int) workingDuration.toMinutes();

        /** 총 근무 시간 %시간 %분 형식 (String)으로 변환하기 */

//        int totalWorkingHoursValue = totalWorkingHours / 60;
//        int totalWorkingMinutesValue = totalWorkingHours % 60;
//
//        String totalWorkingTimeString = String.format("%d시간 %d분", totalWorkingHoursValue, totalWorkingMinutesValue);
//        System.out.println(totalWorkingTimeString);

        UpdateTimeOfCommuteDTO updateTimeOfCommute = new UpdateTimeOfCommuteDTO(
                commuteNo,
                endWork,
                workingStatus,
                totalWorkingHours
        );

        //when
        Map<String, Object> result = new HashMap<>();
        result = commuteService.updateTimeOfCommuteByCommuteNo(updateTimeOfCommute);

        //then
        Assertions.assertTrue((Boolean) result.get("result"));
    }

    @DisplayName("부서별 출퇴근 내역 조회 테스트")
    @Test
//    @Transactional
    void testSelectCommuteListByDepartNo() {
        //given
        int departNo = 1;
        LocalDate date = LocalDate.now();

        /** 전체 출퇴근 내역 조회시 월간 조회에 사용할 변수들 */
        LocalDate startDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate preStartDayOfMonth = startDayOfMonth.minusMonths(1);
        LocalDate preEndDayOfMonth = endDayOfMonth.minusMonths(1);
        LocalDate nextStartDayOfMonth = startDayOfMonth.plusMonths(1);
        LocalDate nextEndDayOfMonth = endDayOfMonth.plusMonths(1);

        //when
        List<CommuteDTO> departCommuteList = commuteService.selectCommuteListByDepartNo(departNo, startDayOfMonth, endDayOfMonth);

        //then
        Assertions.assertTrue(!departCommuteList.isEmpty());
    }

    @DisplayName("memberId 별로 출퇴근 내역 조회 테스트")
    @Test
//    @Transactional
    void testSelectCommuteListByMemberId() {
        //given
        int memberId = 2024001001;
        LocalDate date = LocalDate.now();

        /** 출퇴근 내역 조회시 주간 조회에 사용할 변수들 */
        LocalDate startWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDate preStartWeek = startWeek.minusWeeks(1);
        LocalDate preEndWeek = endWeek.minusWeeks(1);
        LocalDate nextStartWeek = startWeek.plusWeeks(1);
        LocalDate nextEndWeek = endWeek.plusWeeks(1);

        //when
        List<CommuteDTO> commuteList = commuteService.selectCommuteListByMemberId(memberId, startWeek, endWeek);

        //then
        Assertions.assertTrue(!commuteList.isEmpty());
    }

    private static Stream<Arguments> getCorrectTimeOfCommute() {
        return Stream.of(
                Arguments.of(
                        1,
                        "09:00"
                        ,null
                        ,"엘리베이터 점검으로 인해 지각으로 처리되었습니다."
                        ,LocalDate.now()
                        ,"대기"
                        ,null
                        ,null
                )
        );
    }
    @DisplayName("출퇴근 시간 정정 등록 테스트")
    @ParameterizedTest
    @MethodSource("getCorrectTimeOfCommute")
    void testInsertRequestForCorrect(int commuteNo, String reqStartWork, String reqEndWork,
                                     String reasonForCorr, LocalDate corrRegistrationDate, String corrStatus,
                                     String reasonForRejection, LocalDate corrProcessingDate) {
        //given
        CorrectionDTO newCorrection = new CorrectionDTO(
                commuteNo,
                reqStartWork,
                reqEndWork,
                reasonForCorr,
                corrRegistrationDate,
                corrStatus,
                reasonForRejection,
                corrProcessingDate
        );

        //when

        //then
        Assertions.assertDoesNotThrow(
                () -> commuteService.insertRequestForCorrect(newCorrection));
    }

    @DisplayName("출퇴근 시간 정정 처리 테스트")
    @Test
    void testUpdateProcessForCorrectByCorrNo() {
        /** 출퇴근 정정 요청 내역, 출퇴근 내역 2개를 update 해야 함!! */

        //given
        int corrNo = 3;             // 정정 요청 번호
        String corrStatus = "승인";
        String reasonForRejection = null;
        LocalDate corrProcessingDate = LocalDate.now();

        UpdateProcessForCorrectionDTO updateProcessForCorrection = new UpdateProcessForCorrectionDTO(
                corrNo,
                corrStatus,
                reasonForRejection,
                corrProcessingDate
        );

        //when
        Map<String, Object> result = new HashMap<>();
        result = commuteService.updateProcessForCorrectByCorrNo(updateProcessForCorrection);

        //then
        Assertions.assertTrue((Boolean) result.get("result"));
    }

    @DisplayName("전체 출퇴근 시간 정정 내역 조회 테스트")
    @Test
    void testSelectRequestForCorrect() {
        //given
        LocalDate date = LocalDate.now();
        LocalDate startDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        Pageable pageable = Pageable.ofSize(10);

        //when
        Page<CorrectionDTO> correctionlist = commuteService.selectReqeustForCorrectList(startDayOfMonth, endDayOfMonth, pageable);

        //then
        Assertions.assertNotNull(correctionlist);
    }

    @DisplayName("memberId 별로 출퇴근 시간 정정 내역 조회 테스트")
    @Test
    void testSelectRequestForCorrectByMemberId() {
        //given
        int memberId = 2024001001;
        LocalDate date = LocalDate.now();
        LocalDate startDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        Pageable pageable = Pageable.ofSize(10);

        //when
        Page<CorrectionDTO> correctionList = commuteService.selectRequestForCorrectListByMemberId(memberId, startDayOfMonth, endDayOfMonth, pageable);

        //then
        Assertions.assertTrue(!correctionList.isEmpty());
    }

    @DisplayName("출퇴근 시간 정정 요청 상세 조회 테스트")
    @Test
    void testSelectRequestForCorrectByCorrNo() {
        //given
        int corrNo = 3;

        //when
        CorrectionDTO correction = commuteService.selectRequestForCorrectByCorrNo(corrNo);

        //then
        Assertions.assertNotNull(correction);
    }

}
