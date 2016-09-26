package com.padc.interactive_training.data.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.padc.interactive_training.InteractiveTrainingApp;

/**
 * Created by NayLinAung on 9/23/16.
 */
public class CoursesContract {

    public static final String CONTENT_AUTHORITY = InteractiveTrainingApp.class.getPackage().getName();
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_COURSES = "courses";
    public static final String PATH_AUTHOR = "course_authors";
    public static final String PATH_COURSE_TAGS = "course_tags";
    public static final String PATH_COURSE_CHAPTERS = "chapters";
    public static final String PATH_COURSE_CATEGORIES = "course_categories";
    public static final String PATH_COURSE_DISCUSSIONS = "discussions";
    public static final String PATH_COURSE_TODOLIST = "todolists";
    public static final String PATH_COURSE_TODOITEM = "todoitems";
    public static final String PATH_LOGIN_USER = "login_user";

    public static final class CourseEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COURSES).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSES;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSES;

        public static final String TABLE_NAME = "courses";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_COLOR_CODE = "color_code";
        public static final String COLUMN_DARK_COLOR_CODE = "dark_color_code";
        public static final String COLUMN_PROGRESS_COLOR_CODE = "progress_color_code";
        public static final String COLUMN_COVER_PHOTO_URL = "cover_photo_url";
        public static final String COLUMN_LIKE_COUNT = "like_count";
        public static final String COLUMN_AUTHOR_NAME = "author_name";
        public static final String COLUMN_CATEGORY_NAME = "category_name";
        public static final String COLUMN_LAST_ACCESS_CARD = "last_access_card";

        public static Uri buildCourseUri(long id) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attractions/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildCourseUriWithTitle(String courseTitle) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attractions?title="Yangon"
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_TITLE, courseTitle)
                    .build();
        }

        public static String getTitleFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_TITLE);
        }
    }

    public static final class AuthorEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_AUTHOR).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_AUTHOR;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_AUTHOR;

        public static final String TABLE_NAME = "authors";

        public static final String COLUMN_AUTHOR_NAME = "author_name";
        public static final String COLUMN_PROFILE_PHOTO_URL = "profile_photo_url";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_DESCRIPTION = "description";

        public static Uri buildAuthorUri(long id) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildAuthorUriWithAuthorName(String authorName) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images?attraction_title=Yangon
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_AUTHOR_NAME, authorName)
                    .build();
        }

        public static String getAuthorNameFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_AUTHOR_NAME);
        }

        public static Uri buildAuthorUriWithEmail(String email) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images?attraction_title=Yangon
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_EMAIL, email)
                    .build();
        }

        public static String getEmailFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_EMAIL);
        }
    }

    public static final class CourseTagEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COURSE_TAGS).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_TAGS;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_TAGS;

        public static final String TABLE_NAME = "course_tags";

        public static final String COLUMN_COURSE_ID = "course_id";
        public static final String COLUMN_TAG_NAME = "tag_name";

        public static Uri buildCourseTagUri(long id) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildCourseTagUriWithCourseId(String courseId) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images?attraction_title=Yangon
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_COURSE_ID, courseId)
                    .build();
        }

        public static String getCourseIdFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_COURSE_ID);
        }
    }

    public static final class CourseCategoryEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COURSE_CATEGORIES).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_CATEGORIES;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_CATEGORIES;

        public static final String TABLE_NAME = "course_categories";

        public static final String COLUMN_CATEGORY_NAME = "category_name";
        public static final String COLUMN_COURSE_TITLE = "course_title";

        public static Uri buildCourseCategoryUri(long id) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildCourseCategoryUriWithCategoryName(String categoryName) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attractions?title="Yangon"
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_CATEGORY_NAME, categoryName)
                    .build();
        }

        public static String getCategoryNameFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_CATEGORY_NAME);
        }
    }

    public static final class ChapterEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COURSE_CHAPTERS).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_CHAPTERS;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_CHAPTERS;

        public static final String TABLE_NAME = "chapters";

        public static final String COLUMN_COURSE_TITLE = "course_id";
        public static final String COLUMN_CHAPTER_NUMBER = "chapter_number";
        public static final String COLUMN_CHAPTER_TITLE = "chapter_title";
        public static final String COLUMN_CHAPTER_BRIEF = "chapter_brief";
        public static final String COLUMN_DURATION = "duration";

        public static Uri buildChapterUri(long id) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildChapterUriWithCourseTitle(String courseTitle) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images?attraction_title=Yangon
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_COURSE_TITLE, courseTitle)
                    .build();
        }

        public static String getCourseIdFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_COURSE_TITLE);
        }
    }

    public static final class TodoListEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COURSE_TODOLIST).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_TODOLIST;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_TODOLIST;

        public static final String TABLE_NAME = "todo_list";

        public static final String COLUMN_TITLE = "title";

        public static Uri buildTodoListUri(long id) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildTodoListWithTitle(String title) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images?attraction_title=Yangon
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_TITLE, title)
                    .build();
        }

        public static String getTitleFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_TITLE);
        }
    }

    public static final class TodoItemEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COURSE_TODOITEM).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_TODOITEM;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_TODOITEM;

        public static final String TABLE_NAME = "todo_item";

        public static final String COLUMN_TODO_LIST_ID = "todo_list_id";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_CHECKED = "checked";

        public static Uri buildTodoItemUri(long id) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildTodoItemWithTodoListId(String todoListId) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images?attraction_title=Yangon
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_TODO_LIST_ID, todoListId)
                    .build();
        }

        public static String getTodoListIdFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_TODO_LIST_ID);
        }
    }

    public static final class LoginUserEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOGIN_USER).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOGIN_USER;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOGIN_USER;

        public static final String TABLE_NAME = "login_user";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_ACCESS_TOKEN = "access_token";
        public static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_REGISTERED_DATE = "registered_date";
        public static final String COLUMN_LAST_USED_DATE = "last_use_date";
        public static final String COLUMN_PROFILE_PICTURE = "profile_picture";
        public static final String COLUMN_COVER_PICTURE = "cover_picture";
        public static final String COLUMN_FACEBOOK_ID = "facebook_id";

        public static Uri buildLoginUserUri(long id) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/login_user/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
