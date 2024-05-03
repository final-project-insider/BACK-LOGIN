package com.insider.login.other.survey.repository;

import com.insider.login.other.survey.entity.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Integer> {

}
