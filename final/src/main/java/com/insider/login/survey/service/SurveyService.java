package com.insider.login.survey.service;

import com.insider.login.survey.dto.SurveyAnswerDTO;
import com.insider.login.survey.dto.SurveyDTO;
import com.insider.login.survey.dto.SurveyResponseDTO;
import com.insider.login.survey.entity.Survey;
import com.insider.login.survey.entity.SurveyAnswer;
import com.insider.login.survey.entity.SurveyResponse;
import com.insider.login.survey.repository.SurveyAnswerRepository;
import com.insider.login.survey.repository.SurveyRepository;
import com.insider.login.survey.repository.SurveyResponseRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final ModelMapper modelMapper;

    public SurveyService(SurveyRepository surveyRepository, SurveyAnswerRepository surveyAnswerRepository, SurveyResponseRepository surveyResponseRepository, ModelMapper modelMapper) {
        this.surveyRepository = surveyRepository;
        this.surveyAnswerRepository = surveyAnswerRepository;
        this.surveyResponseRepository = surveyResponseRepository;
        this.modelMapper = modelMapper;
    }

    public Page<SurveyDTO> selectSurveyList(Pageable pageable, int memberId) {
        try {
            Page<Survey> surveyPage = surveyRepository.findAll(pageable);

            List<SurveyDTO> DTOList = new ArrayList<>();
            for (Survey survey : surveyPage) {
                SurveyDTO surveyDTO = modelMapper.map(survey, SurveyDTO.class);

                // 해당 설문 조사의 답변들을 리스트로 받아옴
                List<SurveyAnswer> answerList = surveyAnswerRepository.findBySurveyNo(survey.getSurveyNo());

                // 답변들에서 답변 번호만 리스트로 추려냄
                List<Integer> answerNoList = answerList.stream()
                        .map(SurveyAnswer::getAnswerNo)
                        .collect(Collectors.toList());

                // 해당 사번이 답변을 등록한 내역이 존재여부 dto에 삽입
                surveyDTO.setSurveyCompleted(surveyResponseRepository.existsByMemberIdAndSurveyAnswerIn(memberId, answerNoList));
                DTOList.add(surveyDTO);
            }

            // DTOList를 기존 pageable 정보를 가진 새로운 페이지로 만들어서 반환
            return new PageImpl<>(DTOList, surveyPage.getPageable(), surveyPage.getTotalElements());
        } catch (Exception e) {
            return Page.empty();
        }

    }

    public SurveyDTO selectSurveyBySuveyNo(int surveyNo) {
        try {
            return modelMapper.map(surveyRepository.findById(surveyNo), SurveyDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public String insertSurvey(SurveyDTO surveyDTO, List<String> answers) {
        try {
            int surveyNo = surveyRepository.save(modelMapper.map(surveyDTO, Survey.class)).getSurveyNo();

            int answerSequence = 1;
            for (String answer : answers) {
                surveyAnswerRepository.save(modelMapper.map(new SurveyAnswerDTO(surveyNo, answerSequence, answer), SurveyAnswer.class));
                answerSequence++;
            }

            return "수요조사 등록 성공";
        } catch (Exception e) {
            return "수요조사 등록 실패";
        }
    }

    @Transactional
    public String deleteSurvey(int surveyNo) {
        try {
            surveyRepository.deleteById(surveyNo);

            return "수요조사 삭제 성공";
        } catch (Exception e) {
            return "수요조사 삭제 실패";
        }
    }

    @Transactional
    public String insertResponse(int surveyAnswerNo, int memberId) {
        try {
            SurveyResponse surveyResponse = modelMapper.map(new SurveyResponseDTO(memberId, surveyAnswerNo), SurveyResponse.class);

            surveyResponseRepository.save(surveyResponse);

            return "수요조사 응답 등록 성공";
        } catch (Exception e) {
            return "수요조사 응답 등록 실패";
        }
    }
}
