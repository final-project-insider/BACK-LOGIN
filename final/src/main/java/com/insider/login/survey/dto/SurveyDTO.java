package com.insider.login.survey.dto;

import java.time.LocalDate;

public class SurveyDTO {
    private int surveyNo;

    private String surveyTitle;

    private String surveyApplyDate;

    private LocalDate surveyStartDate;

    private LocalDate surveyEndDate;

    private String name;

    private boolean surveyCompleted;

    public SurveyDTO() {
    }

    public SurveyDTO(String surveyTitle, String surveyApplyDate, LocalDate surveyStartDate, LocalDate surveyEndDate) {
        this.surveyTitle = surveyTitle;
        this.surveyApplyDate = surveyApplyDate;
        this.surveyStartDate = surveyStartDate;
        this.surveyEndDate = surveyEndDate;
    }

    public SurveyDTO(int surveyNo, String surveyTitle, String surveyApplyDate, LocalDate surveyStartDate, LocalDate surveyEndDate, String name, boolean surveyCompleted) {
        this.surveyNo = surveyNo;
        this.surveyTitle = surveyTitle;
        this.surveyApplyDate = surveyApplyDate;
        this.surveyStartDate = surveyStartDate;
        this.surveyEndDate = surveyEndDate;
        this.name = name;
        this.surveyCompleted = surveyCompleted;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSurveyCompleted() {
        return surveyCompleted;
    }

    public void setSurveyCompleted(boolean surveyCompleted) {
        this.surveyCompleted = surveyCompleted;
    }

    @Override
    public String toString() {
        return "SurveyDTO{" +
                "surveyNo=" + surveyNo +
                ", surveyTitle='" + surveyTitle + '\'' +
                ", surveyApplyDate='" + surveyApplyDate + '\'' +
                ", surveyStartDate=" + surveyStartDate +
                ", surveyEndDate=" + surveyEndDate +
                ", name='" + name + '\'' +
                ", surveyCompleted=" + surveyCompleted +
                '}';
    }
}
