package com.insider.login.calendar.service;

import com.insider.login.calendar.dto.CalendarDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
public class CalendarServiceTests {

    @Autowired
    private CalendarService calendarService;

    @Test
    @DisplayName("월간 일정 조회")
    void testSelectCalendarMonthly() {
        // given
        String department = "개발팀";

        // when
        List<CalendarDTO> calendarList = calendarService.selectCalendar(department);

        // then
        Assertions.assertFalse(calendarList.isEmpty());
        calendarList.forEach(System.out::println);
    }

    @Test
    @DisplayName("일정 등록")
    void testInsertCalendar() {
        // given
        String calendarName = "테스트 일정";
        LocalDateTime calendarStart = LocalDateTime.of(2024, 4, 10, 12, 30, 0);
        LocalDateTime calendarEnd = LocalDateTime.of(2024, 4, 11, 12, 30, 0);
        String color = "red";
        int registrantId = 200401023;
        String department = "all";

        CalendarDTO calendarDTO = new CalendarDTO(calendarName, calendarStart, calendarEnd, color, department, registrantId);

        // when
        String result = calendarService.insertCalendar(calendarDTO);

        // then
        Assertions.assertEquals(result, "일정 등록 성공");

    }

    @Test
    @DisplayName("일정 수정")
    void testUpdateCalendar() {
        // given
        int calendarNo = 1;
        String calendarName = "수정할 일정";
        LocalDateTime calendarStart = LocalDateTime.of(2024, 7, 11, 12, 30, 0);
        LocalDateTime calendarEnd = LocalDateTime.of(2024, 7, 12, 12, 30, 0);
        String color = "red";
        String department = "all";
        int registrantId = 200401023;
        CalendarDTO calendarDTO = new CalendarDTO(calendarNo, calendarName, calendarStart, calendarEnd, color, department, registrantId);

        // when
        String result = calendarService.updateCalendar(calendarDTO);

        // then
        Assertions.assertEquals(result, "일정 수정 성공");

    }

    @Test
    @DisplayName("일정 삭제")
    void testDeleteCalendar() {
        // given
        int calendarNo = 1;

        // when
        String result = calendarService.deleteCalendar(calendarNo);

        // then
        Assertions.assertEquals(result, "일정 삭제 성공");

    }
}
