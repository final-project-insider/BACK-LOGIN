package com.insider.login.survey.repository;

import com.insider.login.survey.entity.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Integer> {
    boolean existsByMemberIdAndSurveyAnswerIn(int memberId, List<Integer> answerNoList);
}
