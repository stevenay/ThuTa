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
 * Created by NayLinAung on 9/6/2016.
 */
public class ChapterVO {

    @SerializedName("chapter_id")
    private String chapterId;

    @SerializedName("chapter_number")
    private int chapterNumber;

    @SerializedName("chapter_title")
    private String title;

    @SerializedName("chapter_brief")
    private String chapterBrief;

    @SerializedName("lesson_cards")
    private List<LessonCardVO> lessonCards;

    @SerializedName("duration")
    private Integer durationInMins;

    private Integer lessonCount;

    private Integer questionCount;

    @SerializedName("locked")
    private boolean locked;

    @SerializedName("finished_percentage")
    private Integer finishedPercentage;

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public Integer getFinishedPercentage() {
        return finishedPercentage;
    }

    public void setFinishedPercentage(Integer finishedPercentage) {
        this.finishedPercentage = finishedPercentage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(Integer lessonCount) {
        this.lessonCount = lessonCount;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public String getChapterBrief() {
        return chapterBrief;
    }

    public void setChapterBrief(String chapterBrief) {
        this.chapterBrief = chapterBrief;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Integer getDurationInMins() {
        return durationInMins;
    }

    public void setDurationInMins(Integer durationInMins) {
        this.durationInMins = durationInMins;
    }

    public List<LessonCardVO> getLessonCards() {
        return lessonCards;
    }

    public void setLessonCards(List<LessonCardVO> lessonCards) {
        this.lessonCards = lessonCards;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public static void saveChapters(String courseTitle, List<ChapterVO> chapters) {
        Log.d(InteractiveTrainingApp.TAG, "Method: saveChapters. Loaded chapters: " + chapters.size());

        ContentValues[] chapterCVs = new ContentValues[chapters.size()];
        for (int index = 0; index < chapters.size(); index++) {
            ChapterVO chapter = chapters.get(index);
            chapterCVs[index] = chapter.parseToContentValues(courseTitle);

            LessonCardVO.saveLessonCards(chapter.getChapterId(), courseTitle, chapter.getLessonCards());
            Log.d(InteractiveTrainingApp.TAG, "Method: saveChapters. Chapter Title: " + chapter.getTitle());
        }

        Context context = InteractiveTrainingApp.getContext();
        int insertCount = context.getContentResolver().bulkInsert(CoursesContract.ChapterEntry.CONTENT_URI, chapterCVs);

        Log.d(InteractiveTrainingApp.TAG, "Bulk inserted into chapters table : " + insertCount);
    }

    public static ChapterVO parseFromCursor(Cursor data) {
        ChapterVO chapter = new ChapterVO();

        chapter.chapterId = data.getString(data.getColumnIndex(CoursesContract.ChapterEntry.COLUMN_CHAPTER_ID));
        chapter.title = data.getString(data.getColumnIndex(CoursesContract.ChapterEntry.COLUMN_CHAPTER_TITLE));
        chapter.chapterBrief = data.getString(data.getColumnIndex(CoursesContract.ChapterEntry.COLUMN_CHAPTER_BRIEF));
        chapter.durationInMins = data.getInt(data.getColumnIndex(CoursesContract.ChapterEntry.COLUMN_DURATION));
        chapter.finishedPercentage = data.getInt(data.getColumnIndex(CoursesContract.ChapterEntry.COLUMN_FINISHED_PERCENTAGE));
        chapter.chapterNumber = data.getInt(data.getColumnIndex(CoursesContract.ChapterEntry.COLUMN_CHAPTER_NUMBER));
        chapter.locked = data.getInt(data.getColumnIndex(CoursesContract.ChapterEntry.COLUMN_LOCKED)) == 1 ? true : false;
        chapter.lessonCount = data.getInt(data.getColumnIndex(CoursesContract.ChapterEntry.COLUMN_LESSON_COUNT));

        return chapter;
    }

    public ContentValues parseToContentValues(String courseTitle) {

        ContentValues cv = new ContentValues();
        cv.put(CoursesContract.ChapterEntry.COLUMN_CHAPTER_ID, getChapterId());
        cv.put(CoursesContract.ChapterEntry.COLUMN_CHAPTER_TITLE, getTitle());
        cv.put(CoursesContract.ChapterEntry.COLUMN_CHAPTER_NUMBER, getChapterNumber());
        cv.put(CoursesContract.ChapterEntry.COLUMN_CHAPTER_BRIEF, getChapterBrief());
        cv.put(CoursesContract.ChapterEntry.COLUMN_DURATION, getDurationInMins());
        cv.put(CoursesContract.ChapterEntry.COLUMN_COURSE_TITLE, courseTitle);
        cv.put(CoursesContract.ChapterEntry.COLUMN_FINISHED_PERCENTAGE, getFinishedPercentage());
        cv.put(CoursesContract.ChapterEntry.COLUMN_LOCKED, isLocked() ? 1 : 0);
        cv.put(CoursesContract.ChapterEntry.COLUMN_LESSON_COUNT, lessonCards.size());

        return cv;
    }

    public static List<ChapterVO> loadChapterListByCourseTitle(String courseTitle) {
        Context context = InteractiveTrainingApp.getContext();
        List<ChapterVO> chapters = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(CoursesContract.ChapterEntry.buildChapterUriWithCourseTitle(courseTitle),
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ChapterVO chapterVO = ChapterVO.parseFromCursor(cursor);
                Log.d(InteractiveTrainingApp.TAG, "loadChaptersByCourseTitle " + chapterVO.getTitle());
                chapters.add(chapterVO);
            } while (cursor.moveToNext());
        }

        return chapters;
    }

}
