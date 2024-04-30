package com.insider.login.commute.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.insider.login.commute.dto.CommuteDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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


}
