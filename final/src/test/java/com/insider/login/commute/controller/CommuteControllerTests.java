package com.insider.login.commute.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.insider.login.commute.dto.CommuteDTO;
import com.insider.login.commute.dto.TargetDTO;
import com.insider.login.commute.dto.UpdateTimeOfCommuteDTO;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Target;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommuteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("출근 시간 등록 테스트")
    @Test
    void testInsertTimeOfCommute() throws Exception {
        //given
        int memberId = 2024001003;
        LocalDate workingDate = LocalDate.now();
        LocalTime startWork = LocalTime.now();
        String workingStatus = "근무중";
        Integer totalWorkingHours = 0;

        CommuteDTO newCommute = new CommuteDTO(
                memberId,
                workingDate,
                startWork,
                workingStatus,
                totalWorkingHours
        );

        //when
        MvcResult result = mockMvc.perform(post("/commutes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(newCommute)))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("등록 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }

    @DisplayName("퇴근 시간 등록 테스트")
    @Test
    void testUpdateTimeOfCommute() throws Exception {
        //given
        int commuteNo = 19;
        LocalTime endWork = LocalTime.of(17, 50);
        String workingStatus = "퇴근";
        Duration workingDuration = Duration.between(LocalTime.of(8,55), endWork).minusHours(1);
        int totalWorkingHours = (int) workingDuration.toMinutes();

        UpdateTimeOfCommuteDTO updateCommute = new UpdateTimeOfCommuteDTO(
                endWork,
                workingStatus,
                totalWorkingHours
        );

        //when
        MvcResult result = mockMvc.perform(put("/commutes/{commutesNo}", commuteNo)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(updateCommute)))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatusCode").value(200))
                .andExpect(jsonPath("$.message").value("추가 등록 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }

    @DisplayName("부서별 출퇴근 내역 조회 테스트")
    @Test
    void testSelectRequestForCorrectListByDepartNo() throws Exception {
        //given
        String target = "depart";
        int targetValue = 1;
        LocalDate date = LocalDate.now();

        //when
        MvcResult result = mockMvc.perform(get("/commutes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("target", target)
                                .param("targetValue", String.valueOf(targetValue))
                                .param("date", date.toString()))
        //then
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.httpStatusCode").value(200))
                        .andExpect(jsonPath("$.message").value("조회 성공"))
                        .andExpect(jsonPath("$.results").exists())
                        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }

    @DisplayName("멤버별 출퇴근 내역 조회 테스트")
    @Test
    void testSelectRequestForCorrectListByMemberId() throws Exception {
        //given
        String target = "member";
        int targetValue = 2024001001;
        LocalDate date = LocalDate.now();

        //when
        MvcResult result = mockMvc.perform(get("/commutes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("target", target)
                            .param("targetValue", String.valueOf(targetValue))
                            .param("date", date.toString()))
        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatusCode").value(200))
                .andExpect(jsonPath("$.message").value("조회 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }


}
