package com.insider.login.other.note.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.insider.login.other.note.dto.NoteDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;




import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTests {

    @Autowired
    private MockMvc mockMvc;



    @Test
    @DisplayName("컨트롤러 쪽지 조회 테스트")
    public void testSelectNoteList() throws Exception {

        // given
        int memberNo = 3;
        int receiverId = 3;
        String deleteYn = "N";
        int page = 0;
        int size = 10;
        String sort = "noteNo";
        String direction = "DESC";


        // when
        MvcResult result = mockMvc.perform(get("/members/{memberNo}/notes", memberNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("receiverId", String.valueOf(receiverId))
                        .param("deleteYn", deleteYn)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", sort)
                        .param("direction", direction))
        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatusCode").value(200))
                .andExpect(jsonPath("$.message").value("조회 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }

    @Test
    @DisplayName(" 쪽지 상세 조회 테스트 ")
    public void testSelectNoteByNoteNo() throws Exception {

        // given
        int noteNo = 1;

        // when
        MvcResult result = mockMvc.perform(get("/notes/{noteNo}", noteNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(noteNo)))
        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatusCode").value(200))
                .andExpect(jsonPath("$.message").value("조회 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);


    }

    @Test
    @DisplayName("컨트롤러 쪽지 insert 테스트")
    public void testInsertNote() throws Exception {

        // given
        NoteDTO noteDTO = new NoteDTO(9, "2020-20-20", "제목", "내용", 2, 3, "N");

        // when
        MvcResult result = mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(noteDTO)))
        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("등록 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }

    @Test
    @DisplayName("deleteYn을 이용한 쪽지 삭제여부 update 테스트")
    public void testDeleteNote() throws Exception {

        // given
        int noteNo = 7;
        String deleteYn = "Y";

        // when
        MvcResult result = mockMvc.perform(put("/notes/{noteNo}", noteNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("deleteYn", deleteYn))
        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatusCode").value(200))
                .andExpect(jsonPath("$.message").value("삭제 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }





}


