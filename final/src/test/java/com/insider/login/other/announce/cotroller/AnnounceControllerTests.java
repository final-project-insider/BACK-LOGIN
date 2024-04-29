package com.insider.login.other.announce.cotroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insider.login.common.CommonController;
import com.insider.login.other.announce.dto.AncFileDTO;
import com.insider.login.other.announce.dto.AnnounceDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AnnounceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("공지사항 전체 조회 테스트")
    public void testSelectAncList() throws Exception {

        // given
        Pageable pageable = Pageable.ofSize(10);
        int page = 0;
        int size = 10;
        String sort = "ancNo";
        String direction = "DESC";

        // when
        MvcResult result = mockMvc.perform(get("/announces")
                .contentType(MediaType.APPLICATION_JSON)
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
    @DisplayName("공지사항 상세 조회 테스트")
    public void selectAncWithFile() throws Exception {

        // given
        int ancNo = 27;

        // when
        MvcResult result = mockMvc.perform(get("/announces/{ancNo}", ancNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ancNo)))
                // then
                .andExpect(status().isOk())
                .andReturn();


        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);


    }


    @Test
    @DisplayName("공지사항 + 파일 insert 테스트")
    public void testInsertAnc() throws Exception {
        // given
        AnnounceDTO announceDTO = new AnnounceDTO();
        announceDTO.setAncNo(30);
        announceDTO.setAncContent("내용");
        announceDTO.setAncTitle("제목");
        announceDTO.setAncDate("2020-11-11");
        announceDTO.setHits(0);
        announceDTO.setAncWriter("김고무");
        announceDTO.setFilePath("C:\\Users\\simko\\Desktop\\file\\" + "text.txt");
        System.out.println(announceDTO);

        // JSON 데이터를 문자열로 변환하여 파일에 추가
        String json = new ObjectMapper().writeValueAsString(announceDTO);
        MockMultipartFile jsonFile = new MockMultipartFile("announceDTO", "",
                MediaType.APPLICATION_JSON_VALUE, json.getBytes());

        // 테스트할 파일 생성
        MockMultipartFile files = new MockMultipartFile("files", "test.txt",
                MediaType.TEXT_PLAIN_VALUE, "test file content".getBytes());
        // when
        // mockMvc를 사용하여 요청 전송
        mockMvc.perform(multipart("/announces")
                        .file(jsonFile)
                        .file(files)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("공지사항 수정 테스트")
    public void testUpdateAnc() throws Exception{

        //given
        int ancNo = 1;
        String ancTitle = "컨트롤러 공지사항 제목 수정";
        String ancContent = "컨트롤러 공지사항 본문 수정";

        // when
        MvcResult result = mockMvc.perform(put("/announces/{ancNo}", ancNo)
                .contentType(MediaType.APPLICATION_JSON)
                .param("ancTitle", ancTitle)
                .param("ancContent", ancContent))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatusCode").value(200))
                .andExpect(jsonPath("$.message").value("수정 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
    }


    @Test
    @DisplayName("컨트롤러 공지사항 삭제 테스트")
    public void testDeleteAncAndFile() throws Exception {

        // given
        int ancNo = 25;


        // when
        MvcResult result = mockMvc.perform(delete("/announces/{ancNo}", ancNo)
                    .contentType(MediaType.APPLICATION_JSON))
            //then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.httpStatusCode").value(200))
            .andExpect(jsonPath("$.message").value("삭제 성공"))
            .andExpect(jsonPath("$.results").exists())
            .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

    }


}
