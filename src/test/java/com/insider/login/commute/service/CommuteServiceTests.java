package com.insider.login.commute.service;

import com.insider.login.commute.dto.CommuteDTO;
import com.insider.login.commute.dto.CorrectionDTO;
import com.insider.login.commute.dto.UpdateProcessForCorrectionDTO;
import com.insider.login.commute.dto.UpdateTimeOfCommuteDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@SpringBootTest
public class CommuteServiceTests {

    @Autowired
    private CommuteService commuteService;

    private static Stream<Arguments> getStartWork() {
        return Stream.of(
                Arguments.of(
                        240401835,
//                        LocalDate.now(),
                        LocalDate.of(2024,04,29),
                        LocalTime.of(8,55),
                        "근무중",
                        0
                )
        );
    }

    @DisplayName("출근 시간 등록 테스트")
    @ParameterizedTest
    @MethodSource("getStartWork")
//    @Transactional
    void testInsertTimeOfCommute(int memberId, LocalDate workingDate, LocalTime startWork,
                                 String workingStatus, Integer totalWorkingHours) {
        //given
        CommuteDTO newCommute = new CommuteDTO(
                memberId,
                workingDate,
                startWork,
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
        int commuteNo = 35;
        LocalTime endWork = LocalTime.of(18,00);
        String workingStatus = "퇴근";

        /** 총 근무 시간 %%% 분 형식 (int)으로 만들기 */
        Duration workingDuration = Duration.between(LocalTime.of(8,55), endWork).minusHours(1);
        int totalWorkingHours = (int) workingDuration.toMinutes();

        UpdateTimeOfCommuteDTO updateTimeOfCommute = new UpdateTimeOfCommuteDTO(
                endWork,
                workingStatus,
                totalWorkingHours
        );

        //when
        Map<String, Object> result = new HashMap<>();
        result = commuteService.updateTimeOfCommuteByCommuteNo(commuteNo, updateTimeOfCommute);

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

        //when
        List<CommuteDTO> departCommuteList = commuteService.selectCommuteListByDepartNo(departNo, startDayOfMonth, endDayOfMonth);

        //then
        Assertions.assertNotNull(departCommuteList);
        departCommuteList.forEach(commute -> System.out.println("commute : " + commute));
    }

    @DisplayName("멤버별 출퇴근 내역 조회 테스트")
    @Test
//    @Transactional
    void testSelectCommuteListByMemberId() {
        //given
        int memberId = 240401835;
        LocalDate date = LocalDate.now();

        /** 출퇴근 내역 조회시 주간 조회에 사용할 변수들 */
        LocalDate startWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        //when
        List<CommuteDTO> commuteList = commuteService.selectCommuteListByMemberId(memberId, startWeek, endWeek);

        //then
        Assertions.assertNotNull(commuteList);
        commuteList.forEach(commute -> System.out.println("commute : " + commute));
    }

    private static Stream<Arguments> getCorrectTimeOfCommute() {
        return Stream.of(
                Arguments.of(
                        1
                        ,null
                        ,"20:00"
                        ,"시스템 에러로 인해 야근 시 퇴근시간에 오류가 있습니다."
                        ,LocalDate.now()
                        ,"대기"
                )
        );
    }
    @DisplayName("출퇴근 시간 정정 등록 테스트")
    @ParameterizedTest
    @MethodSource("getCorrectTimeOfCommute")
    void testInsertRequestForCorrect(int commuteNo, String reqStartWork, String reqEndWork,
                                     String reasonForCorr, LocalDate corrRegistrationDate, String corrStatus) {
        //given
        CorrectionDTO newCorrection = new CorrectionDTO(
                commuteNo,
                reqStartWork,
                reqEndWork,
                reasonForCorr,
                corrRegistrationDate,
                corrStatus
        );

        //when

        //then
        Assertions.assertDoesNotThrow(
                () -> commuteService.insertRequestForCorrect(newCorrection));
    }

    @DisplayName("출퇴근 시간 정정 처리 테스트")
    @Test
    void testUpdateProcessForCorrectByCorrNo() {
        //given
        /** case 1. 정정 처리 - 승인 */
        int corrNo = 30;
        String corrStatus = "승인";
        LocalDate corrProcessingDate = LocalDate.now();

        UpdateProcessForCorrectionDTO updateProcessForCorrection = new UpdateProcessForCorrectionDTO(
                corrStatus,
                corrProcessingDate
        );

        /** case 2. 정정 처리 - 반려 */
//        int corrNo = 30;
//        String corrStatus = "반려";
//        String reasonForRejection = "적절한 정정 사유에 해당하지 않습니다.";
//        LocalDate corrProcessingDate = LocalDate.now();
//
//        UpdateProcessForCorrectionDTO updateProcessForCorrection = new UpdateProcessForCorrectionDTO(
//                corrStatus,
//                reasonForRejection,
//                corrProcessingDate
//        );

        //when
        Map<String, Object> result = new HashMap<>();
        result = commuteService.updateProcessForCorrectByCorrNo(corrNo, updateProcessForCorrection);

        //then
        Assertions.assertTrue((Boolean) result.get("result"));
    }

    @DisplayName("전체 출퇴근 시간 정정 내역 조회 테스트")
    @Test
    void testSelectRequestForCorrectList() {
        //given
        LocalDate date = LocalDate.now();
        LocalDate startDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        Pageable pageable = Pageable.ofSize(10);

        //when
        Page<CorrectionDTO> correctionList = commuteService.selectRequestForCorrectList(startDayOfMonth, endDayOfMonth, pageable);

        //then
        Assertions.assertNotNull(correctionList);
        correctionList.forEach(correction -> System.out.println("correction : " + correction));
    }

    @DisplayName("멤버별 출퇴근 시간 정정 내역 조회 테스트")
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
        Assertions.assertNotNull(correctionList);
        correctionList.forEach(correction -> System.out.println("correction : " + correction));
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
        System.out.println("correction : " + correction);
    }

    @DisplayName("멤버별 가장 마지막 출퇴근 번호 찾기 테스트")
    @Test
    void testSearchLastCommuteNoByMemberId() {
        //given
        int memberId = 240401835;

        //when
        CommuteDTO lastCommute = commuteService.searchLastCommuteNoByMemberId(memberId);

        //then
        Assertions.assertEquals(memberId, lastCommute.getMemberId());
    }

}
