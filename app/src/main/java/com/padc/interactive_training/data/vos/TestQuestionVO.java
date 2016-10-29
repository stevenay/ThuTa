package com.padc.interactive_training.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.persistence.CoursesContract;

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
    private List<TestAnswerVO> testAnswers;

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

    public List<TestAnswerVO> getTestAnswers() {
        return testAnswers;
    }

    public void setTestAnswers(List<TestAnswerVO> testAnswers) {
        this.testAnswers = testAnswers;
    }

    public static void saveTestQuestions(String courseTestId, List<TestQuestionVO> testQuestions) {
        Log.d(InteractiveTrainingApp.TAG, "Method: TestQuestions. Loaded items: " + testQuestions.size());

        ContentValues[] testQuestionCVs = new ContentValues[testQuestions.size()];
        for (int index = 0; index < testQuestions.size(); index++) {
            TestQuestionVO testQuestion = testQuestions.get(index);
            testQuestionCVs[index] = testQuestion.parseToContentValues(courseTestId);

            Log.d(InteractiveTrainingApp.TAG, "Method: TestQuestions. Qeustion Text: " + testQuestion.getQuestionText());
        }

        Context context = InteractiveTrainingApp.getContext();
        int insertCount = context.getContentResolver().bulkInsert(CoursesContract.TestQuestionEntry.CONTENT_URI, testQuestionCVs);

        Log.d(InteractiveTrainingApp.TAG, "Bulk inserted into testQuestions table : " + insertCount);
    }

    public ContentValues parseToContentValues(String courseTestId) {
        ContentValues cv = new ContentValues();

        cv.put(CoursesContract.TestQuestionEntry.COLUMN_TEST_ID, courseTestId);
        cv.put(CoursesContract.TestQuestionEntry.COLUMN_QUESTION_ID, this.questionId);
        cv.put(CoursesContract.TestQuestionEntry.COLUMN_QUESTION_TEXT, this.questionText);
        cv.put(CoursesContract.TestQuestionEntry.COLUMN_QUESTION_TYPE, this.questionType);
        cv.put(CoursesContract.TestQuestionEntry.COLUMN_TEST_TYPE, this.testType);

        return cv;
    }
}
