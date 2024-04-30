package com.insider.login.commute.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.insider.login.commute.dto.CommuteDTO;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        int commuteNo = 17;
        LocalTime endWork = LocalTime.of(17, 50);
        String workingStatus = "퇴근";
        Duration workingDuration = Duration.between(LocalTime.of(8,55), endWork).minusHours(1);
        int totalWorkingHours = (int) workingDuration.toMinutes();

        UpdateTimeOfCommuteDTO updateCommute = new UpdateTimeOfCommuteDTO(
                commuteNo,
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


}
