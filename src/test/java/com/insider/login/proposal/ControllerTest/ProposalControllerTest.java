package com.insider.login.proposal.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insider.login.proposal.DTO.ProposalDTO;
import com.insider.login.proposal.Service.ProposalService;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProposalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("건의사항 목록 전체 조회")
    void testSelectProposalList() throws Exception {
        // when
        MvcResult result = mockMvc.perform(get("/")
                        .contentType(MediaType.APPLICATION_JSON))
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
    @DisplayName("건의사항 등록")
    void testRegisterProposal() throws Exception {
        // given
        ProposalDTO proposalDTO = new ProposalDTO("건의사항 내용", 123, LocalDate.now());

        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(proposalDTO)))
                // then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "Proposal 등록 성공");
    }

    @Test
    @DisplayName("건의사항 수정")
    void testModifyProposal() throws Exception {
        // given
        ProposalDTO proposalDTO = new ProposalDTO("수정된 건의사항 내용", 456, LocalDate.now());

        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(proposalDTO)))
                // then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "Proposal 수정 성공");
    }

    @Test
    @DisplayName("건의사항 삭제")
    void testDeleteProposal() throws Exception {
        // given
        int proposalId = 1;

        // when
        MvcResult result = mockMvc.perform(delete("/{id}", proposalId)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "Proposal 삭제 성공");
    }
}
