package com.padc.interactive_training.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.persistence.CoursesContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NayLinAung on 10/29/2016.
 */

public class AnswerVO {

    @SerializedName("answer_id")
    private String answerId;

    @SerializedName("answer_content")
    private String answerContent;

    @SerializedName("is_answer")
    private Boolean isAnswer;

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

    public Boolean getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(Boolean answer) {
        isAnswer = answer;
    }

    public static void saveAnswers(String questionId, List<AnswerVO> answers) {
        Log.d(InteractiveTrainingApp.TAG, "Method: Answers. Loaded items: " + answers.size());

        ContentValues[] answerCVs = new ContentValues[answers.size()];
        for (int index = 0; index < answers.size(); index++) {
            AnswerVO answerVO = answers.get(index);
            answerCVs[index] = answerVO.parseToContentValues(questionId);

            Log.d(InteractiveTrainingApp.TAG, "Method: Answer. Answer Content: " + answerVO.getAnswerContent());
        }

        Context context = InteractiveTrainingApp.getContext();
        int insertCount = context.getContentResolver().bulkInsert(CoursesContract.AnswerEntry.CONTENT_URI, answerCVs);

        Log.d(InteractiveTrainingApp.TAG, "Bulk inserted into testQuestions table : " + insertCount);
    }

    public ContentValues parseToContentValues(String questionId) {
        ContentValues cv = new ContentValues();

        cv.put(CoursesContract.AnswerEntry.COLUMN_QUESTION_ID, questionId);
        cv.put(CoursesContract.AnswerEntry.COLUMN_ANSWER_ID, this.answerId);
        cv.put(CoursesContract.AnswerEntry.COLUMN_ANSWER_CONTENT, this.answerContent);
        cv.put(CoursesContract.AnswerEntry.COLUMN_IS_ANSWER, this.isAnswer ? 1 : 0);

        return cv;
    }

    public static List<AnswerVO> loadAnswersByQuestionId(String questionId) {
        Context context = InteractiveTrainingApp.getContext();
        ArrayList<AnswerVO> answers = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(CoursesContract.AnswerEntry.buildAnswerUriWithQuestionId(questionId),
                null, null, null, null);

        if(cursor != null && cursor.moveToFirst()) {
            do {
                AnswerVO answer = new AnswerVO();
                answer.setAnswerId(cursor.getString(cursor.getColumnIndex(CoursesContract.AnswerEntry.COLUMN_ANSWER_ID)));
                answer.setAnswerContent(cursor.getString(cursor.getColumnIndex(CoursesContract.AnswerEntry.COLUMN_ANSWER_CONTENT)));
                answer.setIsAnswer(cursor.getInt(cursor.getColumnIndex(CoursesContract.AnswerEntry.COLUMN_IS_ANSWER)) == 1 ? true : false);
                answers.add(answer);
            } while (cursor.moveToNext());
        }

        return answers;
    }
}
