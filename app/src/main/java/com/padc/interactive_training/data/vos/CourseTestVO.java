package com.padc.interactive_training.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.persistence.CoursesContract;

import java.util.List;

/**
 * Created by NayLinAung on 10/29/2016.
 */

public class CourseTestVO {

    @SerializedName("course_test_id")
    private String testId;

    @SerializedName("chapter_id")
    private String chapterId;

    @SerializedName("test_type")
    private String testType;

    @SerializedName("allowance_time")
    private int allowanceTime;

    @SerializedName("questions")
    private List<TestQuestionVO> testQuestions;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public int getAllowanceTime() {
        return allowanceTime;
    }

    public void setAllowanceTime(int allowanceTime) {
        this.allowanceTime = allowanceTime;
    }

    public List<TestQuestionVO> getTestQuestions() {
        return testQuestions;
    }

    public void setTestQuestions(List<TestQuestionVO> testQuestions) {
        this.testQuestions = testQuestions;
    }

    public static void saveCourseTest(String courseTitle, CourseTestVO courseTest) {
        ContentValues cv = new ContentValues();
        cv.put(CoursesContract.CourseTestEntry.COLUMN_COURSE_TITLE, courseTitle);
        cv.put(CoursesContract.CourseTestEntry.COLUMN_CHAPTER_ID, courseTest.chapterId);
        cv.put(CoursesContract.CourseTestEntry.COLUMN_ALLOWANCE_TIME, courseTest.allowanceTime);
        cv.put(CoursesContract.CourseTestEntry.COLUMN_TEST_ID, courseTest.getTestId());
        cv.put(CoursesContract.CourseTestEntry.COLUMN_TEST_TYPE, courseTest.getTestType());

        TestQuestionVO.saveTestQuestions(courseTest.getTestId(), courseTest.getTestQuestions());

        Context context = InteractiveTrainingApp.getContext();
        Uri insertedRow = context.getContentResolver().insert(CoursesContract.TodoListEntry.CONTENT_URI, cv);

        if (insertedRow != null)
            Log.d(InteractiveTrainingApp.TAG, "OneRow inserted into todolist table : " + insertedRow.toString());
    }


}
