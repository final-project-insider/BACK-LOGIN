package com.insider.login.other.survey.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insider.login.other.survey.dto.SurveyDTO;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SurveyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("수요조사 목록 전체 조회")
    void testSelectSurveyList() throws Exception {
        // given
        int pageNumber = 0;
        String properties = "surveyNo";
        String direction = "DESC";

        // when
        MvcResult result = mockMvc.perform(get("/surveys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(pageNumber))
                        .param("direction", direction)
                        .param("properties", properties))
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
    @DisplayName("수요조사 상세 조회")
    void testSelectSurveyBySurveyNo() throws Exception {
        // given
        int surveyNo = 1;

        // when
        MvcResult result = mockMvc.perform(get("/surveys/{surveyNo}", surveyNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(surveyNo)))
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
    @DisplayName("수요조사 등록")
    void testInsertSurvey() throws Exception {
        // given
        // 수요조사
        String surveyTitle = "치과예약이 있어";
        String surveyApplyDate = "2024-05-02";
        LocalDate surveyStartDate = LocalDate.of(2024, 5, 1);
        LocalDate surveyEndDate = LocalDate.of(2024, 5, 2);

        // 수요조사 답변
        List<String> answers = new ArrayList<>();
        answers.add("답변1");
        answers.add("답변2");
        answers.add("답변3");

        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/leaveAccruals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new SurveyDTO(surveyTitle, surveyApplyDate, surveyStartDate, surveyEndDate)))
                        .content(new ObjectMapper().writeValueAsString(answers)))

                // then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "수요조사 등록 성공");
    }


    @Test
    @DisplayName("수요조사 삭제")
    void testDeleteSurvey() throws Exception {
        // given
        int surveyNo = 19;

        // when
        MvcResult result = mockMvc.perform(delete("/surveys/{surveyNo}", surveyNo)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "수요조사 삭제 성공");
    }

    @Test
    @DisplayName("수요조사 응답 등록")
    void testInsertResponse() throws Exception {
        // given
        int surveyAnswerNo = 10;
        int memberId = 241201001;

        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/surveyResponses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("memberid", String.valueOf(surveyAnswerNo))
                        .param("page", String.valueOf(memberId)))
        // then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "수요조사 응답 등록 성공");
    }
}

