package com.padc.interactive_training.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.padc.interactive_training.data.persistence.CoursesContract.AuthorEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.ChapterEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.CourseCategoryEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.CourseEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.CourseTagEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.TodoItemEntry;
import com.padc.interactive_training.data.persistence.CoursesContract.TodoListEntry;

/**
 * Created by aung on 7/9/16.
 */
public class CourseDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "attractions.db";

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

            " UNIQUE (" + CourseCategoryEntry.COLUMN_CATEGORY_NAME + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_CHAPTER_TABLE = "CREATE TABLE " + ChapterEntry.TABLE_NAME + " (" +
            ChapterEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ChapterEntry.COLUMN_CHAPTER_NUMBER + " INTEGER NOT NULL, " +
            ChapterEntry.COLUMN_CHAPTER_TITLE + " TEXT NOT NULL, " +
            ChapterEntry.COLUMN_CHAPTER_BRIEF + " TEXT NOT NULL, " +
            ChapterEntry.COLUMN_DURATION + " INTEGER NOT NULL, " +
            ChapterEntry.COLUMN_COURSE_TITLE + " TEXT NOT NULL, " +

            " UNIQUE (" + ChapterEntry.COLUMN_CHAPTER_NUMBER + ", " +
            ChapterEntry.COLUMN_COURSE_TITLE + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_TODO_LIST_TABLE = "CREATE TABLE " + TodoListEntry.TABLE_NAME + " (" +
            TodoListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TodoListEntry.COLUMN_TITLE + " TEXT NOT NULL, " +

            " UNIQUE (" + TodoListEntry.COLUMN_TITLE + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_TODO_ITEM_TABLE = "CREATE TABLE " + TodoItemEntry.TABLE_NAME + " (" +
            TodoItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TodoItemEntry.COLUMN_TODO_LIST_ID + " INTEGER NOT NULL, " +
            TodoItemEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
            TodoItemEntry.COLUMN_CHECKED + " INTEGER NOT NULL, " +

            " UNIQUE (" + TodoItemEntry.COLUMN_DESCRIPTION + ") ON CONFLICT IGNORE" +
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
        sqLiteDatabase.execSQL(SQL_CREATE_TODO_LIST_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TODO_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CourseEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AuthorEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CourseTagEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CourseCategoryEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ChapterEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TodoListEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TodoItemEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
