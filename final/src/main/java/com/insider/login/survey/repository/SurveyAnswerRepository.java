package com.insider.login.survey.repository;

import com.insider.login.survey.entity.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Integer> {
    List<SurveyAnswer> findBySurveyNo(int surveyNo);
}
