package com.insider.login.survey.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Survey {

    @Id
    @Column(name = "survey", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int surveyNo;

    @Column(name = "SURVEY_TITLE")
    private String surveyTitle;

    @Column(name = "SURVEY_APPLY_DATE")
    private String surveyApplyDate;

    @Column(name = "SURVEY_START_DATE")
    private LocalDate surveyStartDate;

    @Column(name = "SURVEY_END_DATE")
    private LocalDate surveyEndDate;

    protected Survey() {
    }

    public Survey(int surveyNo, String surveyTitle, String surveyApplyDate, LocalDate surveyStartDate, LocalDate surveyEndDate) {
        this.surveyNo = surveyNo;
        this.surveyTitle = surveyTitle;
        this.surveyApplyDate = surveyApplyDate;
        this.surveyStartDate = surveyStartDate;
        this.surveyEndDate = surveyEndDate;
    }

    public int getSurveyNo() {
        return surveyNo;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public String getSurveyApplyDate() {
        return surveyApplyDate;
    }

    public LocalDate getSurveyStartDate() {
        return surveyStartDate;
    }

    public LocalDate getSurveyEndDate() {
        return surveyEndDate;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "surveyNo=" + surveyNo +
                ", surveyTitle='" + surveyTitle + '\'' +
                ", surveyApplyDate='" + surveyApplyDate + '\'' +
                ", surveyStartDate=" + surveyStartDate +
                ", surveyEndDate=" + surveyEndDate +
                '}';
    }
}
