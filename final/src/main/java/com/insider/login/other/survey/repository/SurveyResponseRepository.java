package com.insider.login.other.survey.repository;

import com.insider.login.other.survey.entity.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Integer> {
}
