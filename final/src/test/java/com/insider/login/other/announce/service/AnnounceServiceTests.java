package com.insider.login.other.announce.service;

import com.insider.login.common.CommonController;
import com.insider.login.other.announce.controller.FileController;
import com.insider.login.other.announce.dto.AncFileDTO;
import com.insider.login.other.announce.dto.AnnounceDTO;
import com.insider.login.other.announce.entity.Announce;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@SpringBootTest
public class AnnounceServiceTests extends FileController {

    @Autowired
    private AnnounceService announceService;


    @Test
    @DisplayName("공지사항 전체 조회 테스트")
    public void selectAncList() {

        // given
        // 페이지에 보여지는 수
        Pageable pageable = Pageable.ofSize(10);

        // when
        Page<AnnounceDTO> ancList = announceService.selectAncList(pageable);

        // then
        Assertions.assertNotNull(ancList);
        ancList.forEach(anc -> System.out.println("공지사항 목록" + anc));

    }

    @Test
    @DisplayName("공지사항 상세 조회 + 파일")
    public void selectAncWithFile() {

        //given
        int ancNo = 19;

        // when
        Announce announce = announceService.findAncWithFile(ancNo);

        // then
        Assertions.assertNotNull(announce);
        Assertions.assertEquals(announce.getAncNo(),ancNo);

    }

    @Test
    @DisplayName("공지사항 insert 테스트")
    public void insertAnnounce() throws IOException {

        // 테스트할 파일 생성
        // given
        File imageFile = new File(getClass().getClassLoader().getResource("jjang-gu.png").getFile());
        FileInputStream input = new FileInputStream(imageFile);
        MultipartFile multipartFile = new MockMultipartFile("테슷흐.png", imageFile.getName(), "image/png", IOUtils.toByteArray(input));
        String filePath = "C:\\Users\\simko\\Desktop\\file" + "\\" + imageFile.getName();   // 저장경로 + \ + fileName

        System.out.println("🎈" + input);
        AnnounceDTO announceDTO = new AnnounceDTO();
        announceDTO.setAncTitle("테스트 테스트 테스트");
        announceDTO.setAncWriter("김덕배");
        announceDTO.setAncContent("성공의 지름길");
        announceDTO.setHits(0);
        announceDTO.setAncDate("2020-2020-2020");
        announceDTO.setFilePath(filePath);

        // when
        Map<String, Object> result = new HashMap<>();

        try {
            List<MultipartFile> files = new ArrayList<>();
            files.add(multipartFile);
            announceService.insertAncWithFile(announceDTO, files);
            result.put("result", true);
        } catch (Exception e) {
            result.put("result", false);
        }

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.get("result"), true);
    }

    @Test
    @DisplayName("공지사항 수정 테스트")
    public void updateAnc() {

        // given
        int ancNo = 2;
        String ancContent = "공지사항을 수정해보도록 하겠습니다";
        String ancTitle = "공지사항 제목을 수정해보도록 하겠습니다";

        // when
        Map <String, Object> result = new HashMap<>();
        result.put("result", announceService.updateAnc(ancNo, ancTitle,ancContent));

        // then
        Assertions.assertNotNull(result);

    }

    @Test
    @DisplayName("공지사항 삭제 테스트")
    public void deleteAnc() {

        // Given
        int ancNo = 19;

        // When
        Map<String, Object> result = announceService.deleteAncAndFile(ancNo);

        // Then
        Assertions.assertNotNull(result);


    }



}
