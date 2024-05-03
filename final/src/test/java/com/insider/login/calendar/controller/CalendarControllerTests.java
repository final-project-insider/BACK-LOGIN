package com.insider.login.calendar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insider.login.calendar.dto.CalendarCriteriaDTO;
import com.insider.login.calendar.dto.CalendarDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CalendarControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("월간 휴가 조회")
    void testSelectCalendarMonthly() throws Exception {
        // given
        String type = "monthly";
        int year = 2024;
        int month = 4;
        String department = "개발팀";

        // when
        MvcResult result = mockMvc.perform(get("/calendars")
                .contentType(MediaType.APPLICATION_JSON)
                .param("type", type)
                .param("year", String.valueOf(year))
                .param("month", String.valueOf(month))
                .param("department", department))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatusCode").value(200))
                .andExpect(jsonPath("$.message").value("조회 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(response.getStatus(), 200);
    }


    @Test
    @DisplayName("주간 휴가 조회")
    void testSelectCalendarWeekly() throws Exception {
        // given
        String type = "weekly";
        int year = 2024;
        int month = 4;
        int week = 15;
        String department = "회계팀";

        // when
        MvcResult result = mockMvc.perform(get("/calendars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("type", type)
                        .param("year", String.valueOf(year))
                        .param("month", String.valueOf(month))
                        .param("week", String.valueOf(week))
                        .param("department", department))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatusCode").value(200))
                .andExpect(jsonPath("$.message").value("조회 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(response.getStatus(), 200);
    }

    @Test
    @DisplayName("일간 휴가 조회")
    void testSelectCalendarDaily() throws Exception {
        // given
        String type = "daily";
        int year = 2024;
        int month = 4;
        int day = 12;
        String department = "인사팀";

        // when
        MvcResult result = mockMvc.perform(get("/calendars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("type", type)
                        .param("year", String.valueOf(year))
                        .param("month", String.valueOf(month))
                        .param("day", String.valueOf(day))
                        .param("department", department))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatusCode").value(200))
                .andExpect(jsonPath("$.message").value("조회 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(response.getStatus(), 200);
    }

    @Test
    @DisplayName("일정 등록")
    void testInsertCalendar() throws Exception {
        // given
        String calendarName = "테스트 일정";
        LocalDateTime calendarStart = LocalDateTime.of(2024, 4, 10, 12, 30, 0);
        LocalDateTime calendarEnd = LocalDateTime.of(2024, 4, 11, 12, 30, 0);
        String color = "red";
        String department = "회계팀";
        int registrantId = 200401023;

        CalendarDTO calendarDTO = new CalendarDTO(calendarName, calendarStart, calendarEnd, color, department, registrantId);

        // when
        MvcResult result = mockMvc.perform(post("/calendars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(calendarDTO)))
        // then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "일정 등록 성공");
    }

    @Test
    @DisplayName("일정 수정")
    void testUpdateCalendar() throws Exception {
        // given
        int calendarNo = 1;
        String calendarName = "수정할 일정";
        LocalDateTime calendarStart = LocalDateTime.of(2024, 7, 11, 12, 30, 0);
        LocalDateTime calendarEnd = LocalDateTime.of(2024, 7, 12, 12, 30, 0);
        String color = "red";
        String department = "회계팀";
        int registrantId = 200401023;
        CalendarDTO calendarDTO = new CalendarDTO(calendarNo, calendarName, calendarStart, calendarEnd, color, department, registrantId);

        // when
        MvcResult result = mockMvc.perform(put("/calendars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(calendarDTO)))
        // then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "일정 수정 성공");
    }

    @Test
    @DisplayName("일정 삭제")
    void testDeleteCalendar() throws Exception {
        // given
        int calendarNo = 1;

        // when
        MvcResult result = mockMvc.perform(delete("/calendars/{calendarNo}", calendarNo)
                .contentType(MediaType.APPLICATION_JSON))
        // then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "일정 삭제 성공");
    }
}
