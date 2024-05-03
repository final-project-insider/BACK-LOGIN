package com.insider.login.leave.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insider.login.leave.dto.LeaveAccrualDTO;
import com.insider.login.leave.dto.LeaveSubmitDTO;
import com.insider.login.leave.service.LeaveService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Date;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.param;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class LeaveControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("나의 휴가 신청 내역 조회")
    public void testSelectLeaveSubmitListByMemberId() throws Exception {
        // given
        int applicantId = 241201001;
        int pageNumber = 1;
        String properties = "leaveSubNo";
        String direction = "DESC";

        //when
        MvcResult result = mockMvc.perform(get("/leaveSubmits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("memberid", String.valueOf(applicantId))
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
    @DisplayName("전체 휴가 신청 내역 조회")
    public void testSelectLeaveSubmitList() throws Exception {
        // given
        int applicantId = 0;
        int pageNumber = 1;
        String properties = "leaveSubNo";
        String direction = "DESC";

        //when
        MvcResult result = mockMvc.perform(get("/leaveSubmits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("memberid", String.valueOf(applicantId))
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
    @DisplayName("휴가 신청")
    void testInsertSubmit() throws Exception {
        // given
        int applicantId = 241201001;
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO(applicantId, LocalDate.parse("2024-04-10"), LocalDate.parse("2024-04-11"), "2024-04-30", "연차", "휴가 상신입니다.");

        // when
        MvcResult result = mockMvc.perform(post("/leaveSubmits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(leaveSubmitDTO)))

                // then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "신청 등록 성공");
    }

    @Test
    @DisplayName("휴가 신청 취소")
    void testDeleteSubmit() throws Exception {
        // given

        int leaveSubNo = 6;

        // when
        MvcResult result = mockMvc.perform(delete("/leaveSubmits/{LeaveSubNo}", leaveSubNo)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "휴가 취소 성공");
    }

    @Test
    @DisplayName("휴가 취소 요청")
    void testInsertSubmitCancel() throws Exception {
        // given
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO(1, 202312003, LocalDate.parse("2024-04-10"), LocalDate.parse("2024-04-11"), "휴가 취소 상신입니다.");
        leaveSubmitDTO.setLeaveSubApplyDate("2024-04-30");
        // when
        MvcResult result = mockMvc.perform(post("//leaveSubmits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(leaveSubmitDTO)))

                // then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "취소요청 등록 성공");
    }

    @Test
    @DisplayName("발생 내역 조회")
    void testSelectAccrualList() throws Exception {
        // given
        int pageNumber = 1;
        String properties = "leaveSubNo";
        String direction = "DESC";

        // when
        MvcResult result = mockMvc.perform(get("/leaveAccruals")
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
    @DisplayName("휴가 발생")
    void testInsertAccrual() throws Exception {
        // given
        int applicantId = 241201001;
        LeaveAccrualDTO accrualDTO = new LeaveAccrualDTO(applicantId, 3, "예비군");
        int grantorId = 200401023;
        accrualDTO.setGrantorId(grantorId);

        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/leaveAccruals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(accrualDTO)))

                // then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "휴가발생 등록 성공");
    }

    @Test
    @DisplayName("상세 조회")
    void testSelectSubmitByLeaveSubNo() throws Exception {
        // given
        int leaveSubNo = 3;

        // when
        MvcResult result = mockMvc.perform(get("/leaveSubmits/{LeaveSubNo}", leaveSubNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(leaveSubNo)))
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
    @DisplayName("휴가 신청 처리")
    void testUpdateSubimt() throws Exception {
        // given
        int leaveSubNo = 6;
        int approverId = 241201001;
        String decision = "승인";
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO(leaveSubNo, approverId, decision, "2024-05-01");
        // when
        MvcResult result = mockMvc.perform(put("/leaveSubmits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(leaveSubmitDTO)))
        // then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "휴가처리 성공");
    }


}
