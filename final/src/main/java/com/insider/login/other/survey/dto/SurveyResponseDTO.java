package com.insider.login.other.survey.dto;

import lombok.AllArgsConstructor;

public class SurveyResponseDTO {

    private int surveyResponseNo;    // 응답 번호

    private int memberId;            // 응답 제출자

    private int surveyAnswer;        // 답변 번호

    public SurveyResponseDTO() {
    }

    public SurveyResponseDTO(int memberId, int surveyAnswer) {
        this.memberId = memberId;
        this.surveyAnswer = surveyAnswer;
    }

    public int getSurveyResponseNo() {
        return surveyResponseNo;
    }

    public void setSurveyResponseNo(int surveyResponseNo) {
        this.surveyResponseNo = surveyResponseNo;
    }

    public int getSurveyAnswer() {
        return surveyAnswer;
    }

    public void setSurveyAnswer(int surveyAnswer) {
        this.surveyAnswer = surveyAnswer;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "SurveyResponseDTO{" +
                "surveyResponseNo=" + surveyResponseNo +
                ", surveyAnswer=" + surveyAnswer +
                ", memberId=" + memberId +
                '}';
    }
}
