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

public class QuestionVO {

    @SerializedName("question_id")
    private String questionId;

    @SerializedName("question_text")
    private String questionText;

    @SerializedName("question_type")
    private String questionType;

    @SerializedName("test_type")
    private String testType;

    @SerializedName("answers")
    private List<AnswerVO> Answers;

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
        return Answers;
    }

    public void setAnswers(List<AnswerVO> answers) {
        Answers = answers;
    }

    public static void saveTestQuestions(String courseTestId, List<QuestionVO> testQuestions) {
        Log.d(InteractiveTrainingApp.TAG, "Method: TestQuestions. Loaded items: " + testQuestions.size());

        ContentValues[] testQuestionCVs = new ContentValues[testQuestions.size()];
        for (int index = 0; index < testQuestions.size(); index++) {
            QuestionVO testQuestion = testQuestions.get(index);
            AnswerVO.saveAnswers(testQuestion.questionId, testQuestion.getAnswers());
            testQuestionCVs[index] = testQuestion.parseToContentValues(courseTestId);

            Log.d(InteractiveTrainingApp.TAG, "Method: TestQuestions. Qeustion Text: " + testQuestion.getQuestionText());
        }

        Context context = InteractiveTrainingApp.getContext();
        int insertCount = context.getContentResolver().bulkInsert(CoursesContract.QuestionEntry.CONTENT_URI, testQuestionCVs);

        Log.d(InteractiveTrainingApp.TAG, "Bulk inserted into testQuestions table : " + insertCount);
    }

    public ContentValues parseToContentValues(String courseTestId) {
        ContentValues cv = new ContentValues();

        cv.put(CoursesContract.QuestionEntry.COLUMN_LESSON_ID, courseTestId);
        cv.put(CoursesContract.QuestionEntry.COLUMN_QUESTION_ID, this.questionId);
        cv.put(CoursesContract.QuestionEntry.COLUMN_QUESTION_TEXT, this.questionText);
        cv.put(CoursesContract.QuestionEntry.COLUMN_QUESTION_TYPE, this.questionType);

        return cv;
    }

    public static List<QuestionVO> loadQuestionsByLessonId(String lessonId) {
        Context context = InteractiveTrainingApp.getContext();
        ArrayList<QuestionVO> questions = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(CoursesContract.QuestionEntry.buildQuestionUriWithLessonId(lessonId),
                null, null, null, null);

        if(cursor != null && cursor.moveToFirst()) {
            do {
                QuestionVO question = new QuestionVO();
                question.setQuestionId(cursor.getString(cursor.getColumnIndex(CoursesContract.QuestionEntry.COLUMN_QUESTION_ID)));
                question.setQuestionText(cursor.getString(cursor.getColumnIndex(CoursesContract.QuestionEntry.COLUMN_QUESTION_TEXT)));
                question.setQuestionType(cursor.getString(cursor.getColumnIndex(CoursesContract.QuestionEntry.COLUMN_QUESTION_TYPE)));

                question.setAnswers(AnswerVO.loadAnswersByQuestionId(question.getQuestionId()));
                questions.add(question);
            } while (cursor.moveToNext());
        }

        return questions;
    }
}
