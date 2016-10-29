package com.padc.interactive_training.data.vos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NayLinAung on 10/29/2016.
 */

public class AnswerVO {

    @SerializedName("answer_id")
    private String answerId;

    @SerializedName("answer_content")
    private String answerContent;

    @SerializedName("is_answer")
    private String isAnswer;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(String isAnswer) {
        this.isAnswer = isAnswer;
    }
}
