package com.insider.login.commute.service;

import com.insider.login.commute.dto.CommuteDTO;
import com.insider.login.commute.dto.UpdateTimeOfCommuteDTO;
import com.insider.login.commute.entity.Commute;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
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
                        2024001001,
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
    void testUpdateTimeOfCommuteByCommuteNo() {
        //given
        int commuteNo = 2;
        LocalTime endWork = LocalTime.of(18,00);
        String workingStatus = "퇴근";

        /** 총 근무 시간 %%% 분 형식 (int)으로 만들기 */
        Duration workingDuration = Duration.between(LocalTime.of(8,55), endWork).minusHours(1);
        int totalWorkingHours = (int) workingDuration.toMinutes();

        /** 총 근무 시간 %시간 %분 형식 (String)으로 변환하기 (나중에 할거에요 삭제하지마세요ㅠㅠㅠ) */

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

    @DisplayName("출퇴근 내역 조회 테스트")
    @Test
    void testSelectCommuteList() {
        //given


        //when

        //then

    }

}
