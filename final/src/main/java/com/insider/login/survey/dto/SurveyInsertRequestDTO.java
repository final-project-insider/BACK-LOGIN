package com.insider.login.survey.dto;

import java.util.List;

public class SurveyInsertRequestDTO {
    private SurveyDTO surveyDTO;
    private List<String> answers;

    public SurveyInsertRequestDTO() {
    }

    public SurveyInsertRequestDTO(SurveyDTO surveyDTO, List<String> answers) {
        this.surveyDTO = surveyDTO;
        this.answers = answers;
    }

    public SurveyDTO getSurveyDTO() {
        return surveyDTO;
    }

    public void setSurveyDTO(SurveyDTO surveyDTO) {
        this.surveyDTO = surveyDTO;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "SurveyInsertRequestDTO{" +
                "surveyDTO=" + surveyDTO +
                ", answers=" + answers +
                '}';
    }
}
