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
 * Created by NayLinAung on 9/2/2016.
 */
public class CourseVO {

    @SerializedName("course_title")
    private String courseTitle;

    @SerializedName("duration")
    private Integer durationInMinute;

    @SerializedName("color_code")
    private String colorCode;

    @SerializedName("progress_color_code")
    private String progressColorCode;

    @SerializedName("cover_photo_url")
    private String coverPhotoUrl;

    @SerializedName("like_count")
    private int likesCount;

    @SerializedName("course_category")
    private CourseCategoryVO courseCategory;

    @SerializedName("author")
    private AuthorVO author;

    @SerializedName("author_name")
    private String authorName;

    @SerializedName("category_name")
    private String categoryName;

    @SerializedName("chapters")
    private List<ChapterVO> chapters;

    @SerializedName("discussions")
    private List<DiscussionVO> discussions;

//    @SerializedName("last_accessed_card_id")
//    private Integer lastAccessedCardId;

    public String getTitle() {
        return courseTitle;
    }

    public void setTitle(String title) {
        this.courseTitle = title;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public AuthorVO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorVO author) {
        this.author = author;
        this.authorName = author.getAuthorName();
    }

    public Integer getDurationInMinute() {
        return durationInMinute;
    }

    public void setDurationInMinute(Integer durationInMinute) {
        this.durationInMinute = durationInMinute;
    }

    public String getProgressColorCode() {
        return progressColorCode;
    }

    public void setProgressColorCode(String progressColorCode) {
        this.progressColorCode = progressColorCode;
    }

    public String getCoverPhotoUrl() {
        return coverPhotoUrl;
    }

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        this.coverPhotoUrl = coverPhotoUrl;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public CourseCategoryVO getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(CourseCategoryVO courseCategory) {
        this.courseCategory = courseCategory;
        this.categoryName = courseCategory.getCategoryName();
    }

    public List<ChapterVO> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChapterVO> chapters) {
        this.chapters = chapters;
    }

    public List<DiscussionVO> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<DiscussionVO> discussions) {
        this.discussions = discussions;
    }

    public static void saveCourses(List<CourseVO> courseList) {
        Context context = InteractiveTrainingApp.getContext();
        ContentValues[] courseCVs = new ContentValues[courseList.size()];
        for (int index = 0; index < courseList.size(); index++) {
            CourseVO course = courseList.get(index);
            courseCVs[index] = course.parseToContentValues();

//            CourseVO.saveCourseCategory();
//            CourseVO.saveAuthor();
            CourseVO.saveChapters(course.getTitle(), course.getChapters()); // Bulk insert into chapters
        }

        //Bulk insert into attractions.
        int insertedCount = context.getContentResolver().bulkInsert(CoursesContract.CourseEntry.CONTENT_URI, courseCVs);

        Log.d(InteractiveTrainingApp.TAG, "Bulk inserted into course table : " + insertedCount);
    }

    private static void saveChapters(String courseTitle, List<ChapterVO> chapters) {
        Log.d(InteractiveTrainingApp.TAG, "Method: saveChapters. Loaded chapters: " + chapters.size());


        ContentValues[] chapterCVs = new ContentValues[chapters.size()];
        for (int index = 0; index < chapters.size(); index++) {
            ChapterVO chapter = chapters.get(index);

            ContentValues cv = new ContentValues();
            cv.put(CoursesContract.ChapterEntry.COLUMN_CHAPTER_TITLE, chapter.getTitle());
            cv.put(CoursesContract.ChapterEntry.COLUMN_CHAPTER_NUMBER, chapter.getChapterNumber());
            cv.put(CoursesContract.ChapterEntry.COLUMN_CHAPTER_BRIEF, chapter.getChapterBrief());
            cv.put(CoursesContract.ChapterEntry.COLUMN_DURATION, chapter.getDurationInMins());
            cv.put(CoursesContract.ChapterEntry.COLUMN_COURSE_TITLE, courseTitle);

            Log.d(InteractiveTrainingApp.TAG, "Method: saveChapters. Chapter Title: " + chapter.getTitle());

            chapterCVs[index] = cv;
        }

        Context context = InteractiveTrainingApp.getContext();
        int insertCount = context.getContentResolver().bulkInsert(CoursesContract.ChapterEntry.CONTENT_URI, chapterCVs);

        Log.d(InteractiveTrainingApp.TAG, "Bulk inserted into chapters table : " + insertCount);
    }

    private ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(CoursesContract.CourseEntry.COLUMN_TITLE, courseTitle);
        cv.put(CoursesContract.CourseEntry.COLUMN_DURATION, durationInMinute);
        cv.put(CoursesContract.CourseEntry.COLUMN_COLOR_CODE, colorCode);
        cv.put(CoursesContract.CourseEntry.COLUMN_PROGRESS_COLOR_CODE, progressColorCode);
        cv.put(CoursesContract.CourseEntry.COLUMN_COVER_PHOTO_URL, coverPhotoUrl);
        cv.put(CoursesContract.CourseEntry.COLUMN_LIKE_COUNT, likesCount);
        cv.put(CoursesContract.CourseEntry.COLUMN_AUTHOR_NAME, authorName);
        cv.put(CoursesContract.CourseEntry.COLUMN_CATEGORY_NAME, categoryName);
        return cv;
    }

    public static CourseVO parseFromCursor(Cursor data) {
        CourseVO course = new CourseVO();

        course.courseTitle = data.getString(data.getColumnIndex(CoursesContract.CourseEntry.COLUMN_TITLE));
        course.durationInMinute = data.getInt(data.getColumnIndex(CoursesContract.CourseEntry.COLUMN_DURATION));
        course.colorCode = data.getString(data.getColumnIndex(CoursesContract.CourseEntry.COLUMN_COLOR_CODE));
        course.progressColorCode = data.getString(data.getColumnIndex(CoursesContract.CourseEntry.COLUMN_PROGRESS_COLOR_CODE));
        course.coverPhotoUrl = data.getString(data.getColumnIndex(CoursesContract.CourseEntry.COLUMN_COVER_PHOTO_URL));
        course.likesCount = data.getInt(data.getColumnIndex(CoursesContract.CourseEntry.COLUMN_LIKE_COUNT));
        course.authorName = data.getString(data.getColumnIndex(CoursesContract.CourseEntry.COLUMN_AUTHOR_NAME));
        course.categoryName = data.getString(data.getColumnIndex(CoursesContract.CourseEntry.COLUMN_CATEGORY_NAME));

        return course;
    }

    public static AuthorVO loadAuthorByName(String authorName) {
        Context context = InteractiveTrainingApp.getContext();
        AuthorVO author = new AuthorVO();

        Cursor cursor = context.getContentResolver().query(CoursesContract.AuthorEntry.buildAuthorUriWithAuthorName(authorName),
                null, null, null, null);

        if(cursor != null && cursor.moveToFirst()) {
            author.setAuthorName(cursor.getString(cursor.getColumnIndex(CoursesContract.AuthorEntry.COLUMN_AUTHOR_NAME)));
        }

        return author;
    }
}
