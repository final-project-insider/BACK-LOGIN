package com.insider.login.other.survey.service;

import com.insider.login.other.survey.dto.SurveyAnswerDTO;
import com.insider.login.other.survey.dto.SurveyDTO;
import com.insider.login.other.survey.dto.SurveyResponseDTO;
import com.insider.login.other.survey.entity.Survey;
import com.insider.login.other.survey.entity.SurveyAnswer;
import com.insider.login.other.survey.entity.SurveyResponse;
import com.insider.login.other.survey.repository.SurveyAnswerRepository;
import com.insider.login.other.survey.repository.SurveyRepository;
import com.insider.login.other.survey.repository.SurveyResponseRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Page<SurveyDTO> selectSurveyList(Pageable pageable) {
        try {
            Page<Survey> surveyPage = surveyRepository.findAll(pageable);

            List<SurveyDTO> DTOList = new ArrayList<>();
            for (Survey survey : surveyPage) {
                DTOList.add(modelMapper.map(survey, SurveyDTO.class));
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

            return "수요조사 응답 등록 성공";
        } catch (Exception e) {
            return "수요조사 응답 등록 실패";
        }
    }
}
