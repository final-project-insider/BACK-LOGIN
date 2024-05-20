package com.insider.login.other.survey.dto;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class SurveyDTO {
    private int surveyNo;

    private String surveyTitle;

    private String surveyApplyDate;

    private LocalDate surveyStartDate;

    private LocalDate surveyEndDate;

    public SurveyDTO() {
    }

    public SurveyDTO(String surveyTitle, String surveyApplyDate, LocalDate surveyStartDate, LocalDate surveyEndDate) {
        this.surveyTitle = surveyTitle;
        this.surveyApplyDate = surveyApplyDate;
        this.surveyStartDate = surveyStartDate;
        this.surveyEndDate = surveyEndDate;
    }

    public SurveyDTO(int surveyNo, String surveyTitle, String surveyApplyDate, LocalDate surveyStartDate, LocalDate surveyEndDate) {
        this.surveyNo = surveyNo;
        this.surveyTitle = surveyTitle;
        this.surveyApplyDate = surveyApplyDate;
        this.surveyStartDate = surveyStartDate;
        this.surveyEndDate = surveyEndDate;
    }

    public int getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(int surveyNo) {
        this.surveyNo = surveyNo;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public String getSurveyApplyDate() {
        return surveyApplyDate;
    }

    public void setSurveyApplyDate(String surveyApplyDate) {
        this.surveyApplyDate = surveyApplyDate;
    }

    public LocalDate getSurveyStartDate() {
        return surveyStartDate;
    }

    public void setSurveyStartDate(LocalDate surveyStartDate) {
        this.surveyStartDate = surveyStartDate;
    }

    public LocalDate getSurveyEndDate() {
        return surveyEndDate;
    }

    public void setSurveyEndDate(LocalDate surveyEndDate) {
        this.surveyEndDate = surveyEndDate;
    }

    @Override
    public String toString() {
        return "SurveyDTO{" +
                "surveyNo=" + surveyNo +
                ", surveyTitle='" + surveyTitle + '\'' +
                ", surveyApplyDate='" + surveyApplyDate + '\'' +
                ", surveyStartDate=" + surveyStartDate +
                ", surveyEndDate=" + surveyEndDate +
                '}';
    }
}
