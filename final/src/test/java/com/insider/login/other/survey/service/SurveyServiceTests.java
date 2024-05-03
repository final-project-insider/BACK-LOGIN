package com.insider.login.other.survey.service;

import com.insider.login.common.CommonController;
import com.insider.login.other.survey.dto.SurveyDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class SurveyServiceTests extends CommonController {

    @Autowired
    private SurveyService surveyService;

    @Test
    @DisplayName("수요조사 목록 조회")
    void testSelectSurveyList() {
        // given
        //페이징 설정
        int pageNumber = 0;
        String properties = "surveyNo";
        String direction = "DESC";

        Pageable pageable;
        if (!direction.equals("DESC")) {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.ASC, properties));
        } else {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, properties));
        }

        // when
        Page<SurveyDTO> results = surveyService.selectSurveyList(pageable);

        // then
        // 조회한 페이지가 비어있지 않아야 함
        Assertions.assertFalse(results.isEmpty());
        // 해당 페이지에 표시될 요소의 개수가 10개를 넘으면 안 됨
        Assertions.assertFalse(results.getSize() > 10);
        // 가져온 페이지 중 현재 인덱스가 의도한 페이지와 같아아 함
        Assertions.assertEquals(results.getNumber(), pageNumber);
    }

    @Test
    @DisplayName("수요조사 상세 조회")
    void testSelectSurveyBySurveyNo() {
        // given
        int surveyNo = 1;

        // when
        SurveyDTO result = surveyService.selectSurveyBySuveyNo(surveyNo);

        // then
        // 조회 결과가 있어야 함
        Assertions.assertNotNull(result);
        // 조회해온 결과의 신청번호가 의도한 것과 같아야 함
        Assertions.assertEquals(result.getSurveyNo(), surveyNo);

    }

    @Test
    @DisplayName("수요조사 등록")
    void testInsertsurvey() {
        // given
        // 수요조사
        String surveyTitle = "치과예약이 있어";
        String surveyApplyDate = nowDate();
        LocalDate surveyStartDate = LocalDate.of(2024, 5, 1);
        LocalDate surveyEndDate = LocalDate.of(2024, 5, 2);
        // 수요조사 답변
        List<String> answers = new ArrayList<>();
        answers.add("답변1");
        answers.add("답변2");
        answers.add("답변3");
        //페이징 설정
        int pageNumber = 0;
        String properties = "surveyNo";
        String direction = "DESC";

        Pageable pageable;
        if (!direction.equals("DESC")) {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.ASC, properties));
        } else {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, properties));
        }

        // when

        Page<SurveyDTO> before = surveyService.selectSurveyList(pageable);

        String result = surveyService.insertSurvey(new SurveyDTO(surveyTitle, surveyApplyDate, surveyStartDate, surveyEndDate), answers);

        Page<SurveyDTO> after = surveyService.selectSurveyList(pageable);

        // then
        // 삭제에 성공하면 수요조사 전체 목록의 요소 수가 등록 이전보다 증가해야 함
        Assertions.assertTrue(after.getTotalElements() > before.getTotalElements());
        // 성공 메시지를 반환해야 함
        Assertions.assertEquals(result, "수요조사 등록 성공");
    }

    @Test
    @DisplayName("수요조사 삭제")
    void testDeleteSurvey() {
        // given
        int surveyNo = 5;
        //페이징 설정
        int pageNumber = 0;
        String properties = "surveyNo";
        String direction = "DESC";

        Pageable pageable;
        if (!direction.equals("DESC")) {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.ASC, properties));
        } else {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, properties));
        }

        // when

        Page<SurveyDTO> before = surveyService.selectSurveyList(pageable);

        String result = surveyService.deleteSurvey(surveyNo);

        Page<SurveyDTO> after = surveyService.selectSurveyList(pageable);

        // then
        // 삭제에 성공하면 수요조사 전체 목록의 요소 수가 삭제 이전보다 감소해야 함
        Assertions.assertTrue(after.getTotalElements() < before.getTotalElements());
        // 성공 메시지를 반환해야 함
        Assertions.assertEquals(result, "수요조사 삭제 성공");
    }

    @Test
    @DisplayName("수요조사 응답 등록")
    void testInsertResponse() {
        // given
        int surveyAnswerNo = 10;
        int memberId = 241201001;

        // when
        String result = surveyService.insertResponse(surveyAnswerNo, memberId);

        // then
        // 성공 메시지를 반환해야 함
        Assertions.assertEquals(result, "수요조사 응답 등록 성공");
    }


}
