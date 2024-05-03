package com.insider.login.other.survey.entity;

import jakarta.persistence.*;

@Entity
@Table
public class SurveyAnswer {

    @Id
    @Column(name = "ANSWER_NO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int answerNo;       // 답변 번호

    @Column(name = "SURVEY_NO", nullable = false)
    private int surveyNo;       // 수요조사 번호

    @Column(name = "ANSWER_SEQUENCE", nullable = false)
    private int answerSequence; // 답변 순서

    @Column(name = "ANSWER", nullable = false)
    private String answer;      // 답변

    protected SurveyAnswer() {
    }

    public int getAnswerNo() {
        return answerNo;
    }

    public int getSurveyNo() {
        return surveyNo;
    }

    public int getAnswerSequence() {
        return answerSequence;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "SurveyAnswer{" +
                "answerNo=" + answerNo +
                ", surveyNo=" + surveyNo +
                ", answerSequence=" + answerSequence +
                ", answer='" + answer + '\'' +
                '}';
    }
}
