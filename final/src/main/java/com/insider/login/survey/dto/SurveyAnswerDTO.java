package com.insider.login.survey.dto;

public class SurveyAnswerDTO {

    private int answerNo;           // 답변 번호

    private int surveyNo;           // 수요조사 번호

    private int answerSequence;     // 답변 순서

    private String answer;          // 답변

    public SurveyAnswerDTO() {
    }

    public SurveyAnswerDTO(int surveyNo, int answerSequence, String answer) {
        this.surveyNo = surveyNo;
        this.answerSequence = answerSequence;
        this.answer = answer;
    }

    public SurveyAnswerDTO(int answerNo, int surveyNo, int answerSequence, String answer) {
        this.answerNo = answerNo;
        this.surveyNo = surveyNo;
        this.answerSequence = answerSequence;
        this.answer = answer;
    }

    public int getAnswerNo() {
        return answerNo;
    }

    public void setAnswerNo(int answerNo) {
        this.answerNo = answerNo;
    }

    public int getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(int surveyNo) {
        this.surveyNo = surveyNo;
    }

    public int getAnswerSequence() {
        return answerSequence;
    }

    public void setAnswerSequence(int answerSequence) {
        this.answerSequence = answerSequence;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "SurveyAnswerDTO{" +
                "answerNo=" + answerNo +
                ", surveyNo=" + surveyNo +
                ", answerSequence=" + answerSequence +
                ", answer='" + answer + '\'' +
                '}';
    }
}
