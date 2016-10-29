package com.padc.interactive_training.data.vos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NayLinAung on 10/29/2016.
 */

public class TestQuestionVO {

    @SerializedName("question_id")
    private String questionId;

    @SerializedName("question_text")
    private String questionText;

    @SerializedName("question_type")
    private String questionType;

    @SerializedName("test_type")
    private String testType;

    @SerializedName("answers")
    private List<AnswerVO> answers;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public List<AnswerVO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerVO> answers) {
        this.answers = answers;
    }
}
