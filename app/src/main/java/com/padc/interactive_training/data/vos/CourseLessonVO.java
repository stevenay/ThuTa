package com.padc.interactive_training.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.persistence.CoursesContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NayLinAung on 10/29/2016.
 */

public class CourseLessonVO {

    @SerializedName("course_lesson_id")
    private String lessonId;

    @SerializedName("chapter_id")
    private String chapterId;

    @SerializedName("lesson_type")
    private String lessonType;

    @SerializedName("allowance_time")
    private int allowanceTime;

    @SerializedName("questions")
    private List<QuestionVO> questions;

    private boolean finishAccess;

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getLessonType() {
        return lessonType;
    }

    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }

    public int getAllowanceTime() {
        return allowanceTime;
    }

    public void setAllowanceTime(int allowanceTime) {
        this.allowanceTime = allowanceTime;
    }

    public List<QuestionVO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionVO> questions) {
        this.questions = questions;
    }

    public boolean isFinishAccess() {
        return finishAccess;
    }

    public void setFinishAccess(boolean finishAccess) {
        this.finishAccess = finishAccess;
    }

    public static void saveCourseLesson(String courseTitle, CourseLessonVO courseTest) {
        ContentValues cv = new ContentValues();
        cv.put(CoursesContract.CourseLessonEntry.COLUMN_COURSE_TITLE, courseTitle);
        cv.put(CoursesContract.CourseLessonEntry.COLUMN_CHAPTER_ID, courseTest.chapterId);
        cv.put(CoursesContract.CourseLessonEntry.COLUMN_ALLOWANCE_TIME, courseTest.allowanceTime);
        cv.put(CoursesContract.CourseLessonEntry.COLUMN_LESSON_ID, courseTest.getLessonId());
        cv.put(CoursesContract.CourseLessonEntry.COLUMN_LESSON_TYPE, courseTest.getLessonType());

        QuestionVO.saveTestQuestions(courseTest.getLessonId(), courseTest.getQuestions());

        Context context = InteractiveTrainingApp.getContext();
        Uri insertedRow = context.getContentResolver().insert(CoursesContract.CourseLessonEntry.CONTENT_URI, cv);

        if (insertedRow != null)
            Log.d(InteractiveTrainingApp.TAG, "OneRow inserted into todolist table : " + insertedRow.toString());
    }

    public static CourseLessonVO parseFromCursor(Cursor data) {
        CourseLessonVO courseLesson = new CourseLessonVO();

        courseLesson.lessonId = data.getString(data.getColumnIndex(CoursesContract.CourseLessonEntry.COLUMN_LESSON_ID));
        courseLesson.chapterId = data.getString(data.getColumnIndex(CoursesContract.CourseLessonEntry.COLUMN_CHAPTER_ID));
        courseLesson.lessonType = data.getString(data.getColumnIndex(CoursesContract.CourseLessonEntry.COLUMN_LESSON_TYPE));
        courseLesson.allowanceTime = data.getInt(data.getColumnIndex(CoursesContract.CourseLessonEntry.COLUMN_ALLOWANCE_TIME));

        return courseLesson;
    }

    public static List<CourseLessonVO> loadCourseLessonbyCourseTitle(String courseTitle) {
        Context context = InteractiveTrainingApp.getContext();
        List<CourseLessonVO> courseLessons = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(CoursesContract.CourseLessonEntry.buildCourseLessonUriWithCourseTitle(courseTitle),
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                CourseLessonVO courseLessonVO = CourseLessonVO.parseFromCursor(cursor);

                Log.d(InteractiveTrainingApp.TAG, "loadCourseLessonsByCourseTitle " + courseLessonVO.getLessonId());

                courseLessons.add(courseLessonVO);
            } while (cursor.moveToNext());
        }

        return courseLessons;
    }
}
