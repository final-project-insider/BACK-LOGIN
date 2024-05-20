package com.insider.login.other.survey.entity;

import jakarta.persistence.*;

@Entity
@Table
public class SurveyResponse {

    @Id
    @Column(name = "SURVEY_RESPONSE_NO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int surveyResponseNo;       // 응답 번호

    @Column(name = "MEMBER_ID", nullable = false)
    private int memberId;               // 응답 제출자

    @Column(name = "ANSWER_NO", nullable = false)
    private int surveyAnswer;               // 답변 번호

    protected SurveyResponse() {
    }

    public SurveyResponse(int memberId, int surveyAnswer) {
        this.memberId = memberId;
        this.surveyAnswer = surveyAnswer;
    }

    public int getSurveyResponseNo() {
        return surveyResponseNo;
    }

    public void setSurveyResponseNo(int surveyResponseNo) {
        this.surveyResponseNo = surveyResponseNo;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getSurveyAnswer() {
        return surveyAnswer;
    }

    public void setSurveyAnswer(int surveyAnswer) {
        this.surveyAnswer = surveyAnswer;
    }

    @Override
    public String toString() {
        return "SurveyResponse{" +
                "surveyResponseNo=" + surveyResponseNo +
                ", memberId=" + memberId +
                ", surveyAnswer=" + surveyAnswer +
                '}';
    }
}
