package com.padc.interactive_training.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.models.CourseModel;
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

    @SerializedName("dark_color_code")
    private String darkColorCode;

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

    @SerializedName("to_do_list")
    private TodoListVO todoList;

    @SerializedName("last_accessed_card_id")
    private String lastAccessedCardId;

    private int lastAccessCardIndex;

    public int getLastAccessCardIndex() {
        return lastAccessCardIndex;
    }

    public void setLastAccessCardIndex(int lastAccessCardIndex) {
        this.lastAccessCardIndex = lastAccessCardIndex;
    }

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
    }

    public String getLastAccessedCardId() {
        return lastAccessedCardId;
    }

    public void setLastAccessedCardId(String lastAccessedCardId) {
        this.lastAccessedCardId = lastAccessedCardId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public TodoListVO getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoListVO todoList) {
        this.todoList = todoList;
    }

    public static void saveCourses(List<CourseVO> courseList) {
        Context context = InteractiveTrainingApp.getContext();
        ContentValues[] courseCVs = new ContentValues[courseList.size()];
        for (int index = 0; index < courseList.size(); index++) {
            CourseVO course = courseList.get(index);
            courseCVs[index] = course.parseToContentValues();

            CourseVO.saveCourseCategory(course.getTitle(), course.getCourseCategory());
            AuthorVO.saveAuthor(course.getTitle(), course.getAuthor());
            ChapterVO.saveChapters(course.getTitle(), course.getChapters());
            DiscussionVO.saveDiscussions(course.getTitle(), course.getDiscussions());
            TodoListVO.saveTodoList(course.getTitle(), course.getTodoList());
        }

        //Bulk insert into attractions.
        int insertedCount = context.getContentResolver().bulkInsert(CoursesContract.CourseEntry.CONTENT_URI, courseCVs);
        Log.d(InteractiveTrainingApp.TAG, "Bulk inserted into course table : " + insertedCount);
    }

    public static CourseVO loadFeaturedCourse() {
        Context context = InteractiveTrainingApp.getContext();
        CourseVO featuredCourse = new CourseVO();
        Cursor cursor = context.getContentResolver().query(CoursesContract.CourseEntry.CONTENT_URI,
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            featuredCourse = CourseVO.parseFromCursor(cursor);
        }

        return featuredCourse;
    }

    private static void saveCourseCategory(String courseTitle, CourseCategoryVO courseCategory) {
        ContentValues cv = new ContentValues();
        cv.put(CoursesContract.CourseCategoryEntry.COLUMN_CATEGORY_NAME, courseCategory.getCategoryName());
        cv.put(CoursesContract.CourseCategoryEntry.COLUMN_COURSE_TITLE, courseTitle);

        Log.d(InteractiveTrainingApp.TAG, "Method: saveCategories. Category Name: " + courseCategory.getCategoryName());

        Context context = InteractiveTrainingApp.getContext();
        Uri insertedRow = context.getContentResolver().insert(CoursesContract.CourseCategoryEntry.CONTENT_URI, cv);

        if (insertedRow != null)
            Log.d(InteractiveTrainingApp.TAG, "OneRow inserted into category table : " + insertedRow.toString());
    }

    private ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();

        Log.d(InteractiveTrainingApp.TAG, lastAccessedCardId);

        cv.put(CoursesContract.CourseEntry.COLUMN_TITLE, courseTitle);
        cv.put(CoursesContract.CourseEntry.COLUMN_DURATION, durationInMinute);
        cv.put(CoursesContract.CourseEntry.COLUMN_COLOR_CODE, colorCode);
        cv.put(CoursesContract.CourseEntry.COLUMN_DARK_COLOR_CODE, darkColorCode);
        cv.put(CoursesContract.CourseEntry.COLUMN_PROGRESS_COLOR_CODE, progressColorCode);
        cv.put(CoursesContract.CourseEntry.COLUMN_COVER_PHOTO_URL, coverPhotoUrl);
        cv.put(CoursesContract.CourseEntry.COLUMN_LIKE_COUNT, likesCount);
        cv.put(CoursesContract.CourseEntry.COLUMN_AUTHOR_NAME, this.author.getAuthorName());
        cv.put(CoursesContract.CourseEntry.COLUMN_CATEGORY_NAME, this.courseCategory.getCategoryName());
        cv.put(CoursesContract.CourseEntry.COLUMN_LAST_ACCESS_CARD, lastAccessedCardId);

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
}
