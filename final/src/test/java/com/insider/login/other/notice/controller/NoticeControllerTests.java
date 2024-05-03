package com.insider.login.other.notice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.insider.login.other.notice.dto.NoticeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NoticeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("출퇴근 정정 요청 알림 등록 테스트")
    @Test
    void testInsertNoticeOfReqForCorrect() throws Exception {
        //given
        int memberId = 2024001003;
        LocalDateTime noticeDateTime = LocalDateTime.now();
        String noticeType = "출퇴근";
        String noticeContent = "새로운 출퇴근 정정 요청이 있습니다. 확인해주세요.";

        NoticeDTO newNoticeOfReqForCorrect = new NoticeDTO(
                memberId,
                noticeDateTime,
                noticeType,
                noticeContent
        );

        //when
        MvcResult result = mockMvc.perform(post("/notices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(newNoticeOfReqForCorrect)))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("등록 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }

    @DisplayName("출퇴근 정정 처리 알림 등록 테스트")
    @Test
    void testInsertNoticeOfProcessForCorrect() throws Exception {
        //given
        int memberId = 2024001003;
        LocalDateTime noticeDateTime = LocalDateTime.now();
        String noticeType = "출퇴근";
        String noticeContent = "출퇴근 정정 요청이 처리되었습니다. 확인해주세요.";

        NoticeDTO newNoticeOfProcessForCorrect = new NoticeDTO(
                memberId,
                noticeDateTime,
                noticeType,
                noticeContent
        );

        //when
        MvcResult result = mockMvc.perform(post("/notices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(newNoticeOfProcessForCorrect)))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("등록 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }

    @DisplayName("전자결재 상신 알림 등록 테스트")
    @Test
    void testInsertNoticeOfApproval() throws Exception {
        //given
        int memberId = 2024001001;
        LocalDateTime noticeDateTime = LocalDateTime.now();
        String noticeType = "전자결재";
        String noticeContent = "새로운 결재 기안이 있습니다. 확인해주세요.";

        NoticeDTO newNoticeOfApproval = new NoticeDTO(
                memberId,
                noticeDateTime,
                noticeType,
                noticeContent
        );

        //when
        MvcResult result = mockMvc.perform(post("/notices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(newNoticeOfApproval)))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("등록 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);

    }

    @DisplayName("전자결재 결재 알림 등록 테스트")
    @Test
    void testInsertNoticeOfResultForApproval() throws Exception {
        //given
        int memberId = 2024001001;
        LocalDateTime noticeDateTime = LocalDateTime.now();
        String noticeType = "전자결재";
        String noticeContent = "기안하신 결재가 처리되었습니다. 확인해주세요.";

        NoticeDTO newNoticeOfResultForApproval = new NoticeDTO(
                memberId,
                noticeDateTime,
                noticeType,
                noticeContent
        );

        //when
        MvcResult result = mockMvc.perform(post("/notices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(newNoticeOfResultForApproval)))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("등록 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }

    @DisplayName("수신한 알림 내역 조회 테스트")
    @Test
    void testSelectNoticeListByMemberId() throws Exception {
        //given
        int memberId = 2024001001;

        //when
        MvcResult result = mockMvc.perform(get("/notices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("memberId", String.valueOf(memberId)))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatusCode").value(200))
                .andExpect(jsonPath("$.message").value("조회 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }

    @DisplayName("알림 선택 삭제 테스트")
    @Test
    void testDeleteNoticeByNoticeNo() throws Exception {
        //given
        int noticeNo = 7;

        //when
        MvcResult result = mockMvc.perform(delete("/notices/{noticeNo}", noticeNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("noticeNo", String.valueOf(noticeNo)))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatusCode").value(200))
                .andExpect(jsonPath("$.message").value("선택 삭제 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }

    @DisplayName("알림 전체 삭제 테스트")
    @Test
    void testDeleteNoticeListByMemberId() throws Exception {
        //given
        int memberId = 2024001003;

        //when
        MvcResult result = mockMvc.perform(delete("/members/{memberId}/notices", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("memberId", String.valueOf(memberId)))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatusCode").value(200))
                .andExpect(jsonPath("$.message").value("전체 삭제 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }
}
