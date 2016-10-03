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
    public static final String PATH_LESSON_CARDS = "lesson_cards";
    public static final String PATH_COURSE_CATEGORIES = "course_categories";
    public static final String PATH_COURSE_DISCUSSIONS = "discussions";
    public static final String PATH_COURSE_REPLIES = "replies";
    public static final String PATH_COURSE_TODOLIST = "todolists";
    public static final String PATH_COURSE_TODOITEM = "todoitems";
    public static final String PATH_ARTICLES = "articles";
    public static final String PATH_USER = "user";

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

        public static final String COLUMN_CHAPTER_ID = "chapter_id";
        public static final String COLUMN_COURSE_TITLE = "course_id";
        public static final String COLUMN_CHAPTER_NUMBER = "chapter_number";
        public static final String COLUMN_CHAPTER_TITLE = "chapter_title";
        public static final String COLUMN_CHAPTER_BRIEF = "chapter_brief";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_FINISHED_PERCENTAGE = "finished_percentage";
        public static final String COLUMN_LOCKED = "locked";
        public static final String COLUMN_LESSON_COUNT = "lesson_count";

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

        public static String getCourseTitleFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_COURSE_TITLE);
        }
    }

    public static final class LessonCardEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LESSON_CARDS).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LESSON_CARDS;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LESSON_CARDS;

        public static final String TABLE_NAME = "lesson_cards";

        public static final String COLUMN_CARD_ID = "card_id";
        public static final String COLUMN_COURSE_TITLE = "course_title";
        public static final String COLUMN_CHAPTER_ID = "chapter_id";
        public static final String COLUMN_CARD_DESCRIPTION = "card_description";
        public static final String COLUMN_CARD_IMAGE_URL = "card_image_url";
        public static final String COLUMN_CARD_ORDER_NUMBER = "card_order_number";
        public static final String COLUMN_FINISHED = "finished";
        public static final String COLUMN_BOOKMARKED = "bookmarked";

        public static Uri buildLessonCardUri(long id) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildLessonCardUriWithChapterId(String chapterId) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images?attraction_title=Yangon
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_CHAPTER_ID, chapterId)
                    .build();
        }

        public static String getChapterIdFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_CHAPTER_ID);
        }

        public static Uri buildLessonCardUriWithCourseTitle(String courseTitle) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images?attraction_title=Yangon
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_COURSE_TITLE, courseTitle)
                    .build();
        }

        public static String getCourseTitleFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_COURSE_TITLE);
        }
    }

    public static final class DiscussionEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COURSE_DISCUSSIONS).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_DISCUSSIONS;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_DISCUSSIONS;

        public static final String TABLE_NAME = "discussions";

        public static final String COLUMN_COURSE_TITLE = "course_title";
        public static final String COLUMN_DISCUSSION_ID = "discussion_id";
        public static final String COLUMN_DISCUSSION_TITLE = "discussion_title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_POST_DATETIME = "post_datetime";
        public static final String COLUMN_LIKE_COUNT = "like_count";

        public static Uri buildDiscussionUri(long id) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildDiscussionUriWithCourseTitle(String courseTitle) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images?attraction_title=Yangon
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_COURSE_TITLE, courseTitle)
                    .build();
        }

        public static String getCourseTitleFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_COURSE_TITLE);
        }
    }

    public static final class ReplyEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COURSE_REPLIES).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_REPLIES;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_REPLIES;

        public static final String TABLE_NAME = "replies";

        public static final String COLUMN_DISCUSSION_ID = "discussion_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_REPLY = "reply";
        public static final String COLUMN_REPLY_DATETIME = "reply_datetime";
        public static final String COLUMN_LIKE_COUNT = "like_count";

        public static Uri buildReplyUri(long id) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildReplyUriWithDiscussionId(String discussionId) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images?attraction_title=Yangon
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_DISCUSSION_ID, discussionId)
                    .build();
        }

        public static String getDiscussionIdFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_DISCUSSION_ID);
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

        public static final String COLUMN_TODO_LIST_ID = "todo_list_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CARD_ID = "card_id";
        public static final String COLUMN_COURSE_TITLE = "course_title";

        public static Uri buildTodoListUri(long id) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildTodoListWithCourseTitle(String courseTitle) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images?attraction_title=Yangon
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_COURSE_TITLE, courseTitle)
                    .build();
        }

        public static String getCourseTitleFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_COURSE_TITLE);
        }

        public static Uri buildTodoListWithListId(String todoListId) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images?attraction_title=Yangon
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_TODO_LIST_ID, todoListId)
                    .build();
        }

        public static String getListIdFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_TODO_LIST_ID);
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

    public static final class UserEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String TABLE_NAME = "login_user";

        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PROFILE_PICTURE_URL = "profile_picture_url";
        public static final String COLUMN_EMAIL = "email";

        public static Uri buildUserUri(long id) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/login_user/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUserUriWithUserId(String userId) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images?attraction_title=Yangon
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_USER_ID, userId)
                    .build();
        }

        public static String getUserIdFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_USER_ID);
        }
    }

    public static final class ArticleEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTICLES).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTICLES;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTICLES;

        public static final String TABLE_NAME = "articles";

        public static final String COLUMN_ARTICLE_ID = "article_id";
        public static final String COLUMN_PUBLISHED_DATE_TIME = "published_date_time";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_IMG_URL1 = "img_url1";
        public static final String COLUMN_IMG_URL2 = "img_url2";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_ARTICLE_TITLE = "article_title";
        public static final String COLUMN_INTRO_CONTENT = "intro_content";
        public static final String COLUMN_FIRST_HEADING = "first_heading";
        public static final String COLUMN_FIRST_HEADING_CONTENT = "first_heading_content";
        public static final String COLUMN_SECOND_HEADING = "second_heading";
        public static final String COLUMN_SECOND_HEADING_CONTENT = "second_heading_content";
        public static final String COLUMN_THIRD_HEADING = "third_heading";
        public static final String COLUMN_THIRD_HEADING_CONTENT = "third_heading_content";

        public static Uri buildArticleUri(long id) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attractions/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildArticleUriWithId(int aritcleId) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attractions?title="Yangon"
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_ARTICLE_ID, String.valueOf(aritcleId))
                    .build();
        }

        public static String getArticleIdFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_ARTICLE_ID);
        }
    }
}