package com.padc.interactive_training.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.padc.interactive_training.data.persistence.CoursesContract.ArticleEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.AuthorEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.ChapterEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.CourseCategoryEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.CourseEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.CourseTagEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.DiscussionEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.LessonCardEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.ReplyEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.TodoItemEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.TodoListEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.UserEntry;

/**
 * Created by NayLinAung on 7/9/16.
 */
public class CourseDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "courses.db";
    private static final int DATABASE_VERSION = 11;
    private static final String SQL_CREATE_COURSE_TABLE = "CREATE TABLE " + CourseEntry.TABLE_NAME + " (" +
            CourseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CourseEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
            CourseEntry.COLUMN_DURATION + " INTEGER NOT NULL, " +
            CourseEntry.COLUMN_COLOR_CODE + " TEXT NOT NULL, " +
            CourseEntry.COLUMN_DARK_COLOR_CODE + " TEXT NOT NULL, " +
            CourseEntry.COLUMN_PROGRESS_COLOR_CODE + " TEXT NOT NULL, " +
            CourseEntry.COLUMN_COVER_PHOTO_URL + " TEXT NOT NULL, " +
            CourseEntry.COLUMN_LIKE_COUNT + " INTEGER NOT NULL, " +
            CourseEntry.COLUMN_AUTHOR_NAME + " TEXT NOT NULL, " +
            CourseEntry.COLUMN_CATEGORY_NAME + " TEXT NOT NULL, " +
            CourseEntry.COLUMN_LAST_ACCESS_CARD + " TEXT NOT NULL, " +

            " UNIQUE (" + CourseEntry.COLUMN_TITLE + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_AUTHOR_TABLE = "CREATE TABLE " + AuthorEntry.TABLE_NAME + " (" +
            AuthorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            AuthorEntry.COLUMN_AUTHOR_NAME + " TEXT NOT NULL, " +
            AuthorEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
            AuthorEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
            AuthorEntry.COLUMN_PROFILE_PHOTO_URL + " TEXT NOT NULL, " +

            " UNIQUE (" + AuthorEntry.COLUMN_EMAIL + ", " +
            AuthorEntry.COLUMN_AUTHOR_NAME + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_COURSE_TAG_TABLE = "CREATE TABLE " + CourseTagEntry.TABLE_NAME + " (" +
            CourseTagEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CourseTagEntry.COLUMN_COURSE_ID + " INTEGER NOT NULL, " +
            CourseTagEntry.COLUMN_TAG_NAME + " TEXT NOT NULL, " +

            " UNIQUE (" + CourseTagEntry.COLUMN_COURSE_ID + ", " +
            CourseTagEntry.COLUMN_TAG_NAME + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_COURSE_CATEGORY_TABLE = "CREATE TABLE " + CourseCategoryEntry.TABLE_NAME + " (" +
            CourseCategoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CourseCategoryEntry.COLUMN_CATEGORY_NAME + " TEXT NOT NULL, " +
            CourseCategoryEntry.COLUMN_COURSE_TITLE + " TEXT NOT NULL, " +

            " UNIQUE (" + CourseCategoryEntry.COLUMN_CATEGORY_NAME + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_CHAPTER_TABLE = "CREATE TABLE " + ChapterEntry.TABLE_NAME + " (" +
            ChapterEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ChapterEntry.COLUMN_CHAPTER_NUMBER + " INTEGER NOT NULL, " +
            ChapterEntry.COLUMN_CHAPTER_TITLE + " TEXT NOT NULL, " +
            ChapterEntry.COLUMN_CHAPTER_BRIEF + " TEXT NOT NULL, " +
            ChapterEntry.COLUMN_DURATION + " INTEGER NOT NULL, " +
            ChapterEntry.COLUMN_COURSE_TITLE + " TEXT NOT NULL, " +
            ChapterEntry.COLUMN_FINISHED_PERCENTAGE + " INT NOT NULL, " +
            ChapterEntry.COLUMN_LOCKED + " INT NOT NULL, " +
            ChapterEntry.COLUMN_LESSON_COUNT + " INT NOT NULL, " +
            ChapterEntry.COLUMN_CHAPTER_ID + " TEXT NOT NULL, " +

            " UNIQUE (" + ChapterEntry.COLUMN_CHAPTER_ID + ", " +
            ChapterEntry.COLUMN_COURSE_TITLE + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_DISCUSSION_TABLE = "CREATE TABLE " + DiscussionEntry.TABLE_NAME + " (" +
            DiscussionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DiscussionEntry.COLUMN_DISCUSSION_ID + " TEXT NOT NULL, " +
            DiscussionEntry.COLUMN_DISCUSSION_TITLE + " TEXT NOT NULL, " +
            DiscussionEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
            DiscussionEntry.COLUMN_USER_ID + " TEXT NOT NULL, " +
            DiscussionEntry.COLUMN_POST_DATETIME + " TEXT NOT NULL, " +
            DiscussionEntry.COLUMN_LIKE_COUNT + " INTEGER NOT NULL, " +
            DiscussionEntry.COLUMN_COURSE_TITLE + " TEXT NOT NULL, " +

            " UNIQUE (" + DiscussionEntry.COLUMN_DISCUSSION_ID + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_REPLY_TABLE = "CREATE TABLE " + ReplyEntry.TABLE_NAME + " (" +
            ReplyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ReplyEntry.COLUMN_DISCUSSION_ID + " TEXT NOT NULL, " +
            ReplyEntry.COLUMN_REPLY + " TEXT NOT NULL, " +
            ReplyEntry.COLUMN_USER_ID + " TEXT NOT NULL, " +
            ReplyEntry.COLUMN_REPLY_DATETIME + " TEXT NOT NULL, " +
            ReplyEntry.COLUMN_LIKE_COUNT + " INTEGER NOT NULL, " +

            " UNIQUE (" + ReplyEntry.COLUMN_DISCUSSION_ID + ", " +
            ReplyEntry.COLUMN_REPLY + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_LESSON_CARD_TABLE = "CREATE TABLE " + LessonCardEntry.TABLE_NAME + " (" +
            LessonCardEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            LessonCardEntry.COLUMN_COURSE_TITLE + " TEXT NOT NULL, " +
            LessonCardEntry.COLUMN_CHAPTER_ID + " TEXT NOT NULL, " +
            LessonCardEntry.COLUMN_CARD_ID + " TEXT NOT NULL, " +
            LessonCardEntry.COLUMN_CARD_DESCRIPTION + " TEXT NOT NULL, " +
            LessonCardEntry.COLUMN_CARD_ORDER_NUMBER + " INTEGER NOT NULL, " +
            LessonCardEntry.COLUMN_CARD_IMAGE_URL + " INTEGER NOT NULL, " +
            LessonCardEntry.COLUMN_FINISHED + " INTEGER NOT NULL, " +
            LessonCardEntry.COLUMN_BOOKMARKED + " INTEGER NOT NULL, " +

            " UNIQUE (" + LessonCardEntry.COLUMN_CHAPTER_ID + ", " +
            LessonCardEntry.COLUMN_COURSE_TITLE + ", " +
            LessonCardEntry.COLUMN_CARD_DESCRIPTION + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_TODO_LIST_TABLE = "CREATE TABLE " + TodoListEntry.TABLE_NAME + " (" +
            TodoListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TodoListEntry.COLUMN_TODO_LIST_ID + " TEXT NOT NULL, " +
            TodoListEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
            TodoListEntry.COLUMN_CARD_ID + " TEXT NOT NULL, " +
            TodoListEntry.COLUMN_COURSE_TITLE + " TEXT NOT NULL, " +

            " UNIQUE (" + TodoListEntry.COLUMN_TITLE + ", " +
            TodoListEntry.COLUMN_COURSE_TITLE + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_TODO_ITEM_TABLE = "CREATE TABLE " + TodoItemEntry.TABLE_NAME + " (" +
            TodoItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TodoItemEntry.COLUMN_TODO_LIST_ID + " INTEGER NOT NULL, " +
            TodoItemEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
            TodoItemEntry.COLUMN_CHECKED + " INTEGER NOT NULL, " +

            " UNIQUE (" + TodoItemEntry.COLUMN_DESCRIPTION + ", " +
            TodoItemEntry.COLUMN_TODO_LIST_ID + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
            UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            UserEntry.COLUMN_USER_ID + " TEXT NOT NULL, " +
            UserEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            UserEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
            UserEntry.COLUMN_PROFILE_PICTURE_URL + " TEXT NOT NULL, " +

            " UNIQUE (" + UserEntry.COLUMN_USER_ID + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_ARTICLES_TABLE = "CREATE TABLE " + ArticleEntry.TABLE_NAME + " (" +
            ArticleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ArticleEntry.COLUMN_ARTICLE_ID + " INTEGER NOT NULL, " +
            ArticleEntry.COLUMN_PUBLISHED_DATE_TIME + " TEXT NOT NULL, " +
            ArticleEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
            ArticleEntry.COLUMN_IMG_URL1 + " TEXT NOT NULL, " +
            ArticleEntry.COLUMN_IMG_URL2 + " TEXT NOT NULL, " +
            ArticleEntry.COLUMN_STATUS + " TEXT NOT NULL, " +
            ArticleEntry.COLUMN_ARTICLE_TITLE + " TEXT NOT NULL, " +
            ArticleEntry.COLUMN_INTRO_CONTENT + " TEXT NOT NULL, " +
            ArticleEntry.COLUMN_FIRST_HEADING + " TEXT NOT NULL, " +
            ArticleEntry.COLUMN_FIRST_HEADING_CONTENT + " TEXT NOT NULL, " +
            ArticleEntry.COLUMN_SECOND_HEADING + " TEXT NOT NULL, " +
            ArticleEntry.COLUMN_SECOND_HEADING_CONTENT + " TEXT NOT NULL, " +
            ArticleEntry.COLUMN_THIRD_HEADING + " TEXT NOT NULL, " +
            ArticleEntry.COLUMN_THIRD_HEADING_CONTENT + " TEXT NOT NULL, " +


            " UNIQUE (" + ArticleEntry.COLUMN_ARTICLE_ID + ") ON CONFLICT IGNORE" +
            " );";

    public CourseDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_COURSE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_AUTHOR_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_COURSE_TAG_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_COURSE_CATEGORY_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CHAPTER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_LESSON_CARD_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_DISCUSSION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_REPLY_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TODO_LIST_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TODO_ITEM_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ARTICLES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CourseEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AuthorEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CourseTagEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CourseCategoryEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ChapterEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LessonCardEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DiscussionEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ReplyEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TodoListEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TodoItemEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ArticleEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}