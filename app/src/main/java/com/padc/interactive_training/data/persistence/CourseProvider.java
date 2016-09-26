package com.padc.interactive_training.data.persistence;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by NayLinAung on 9/26/16.
 */
public class CourseProvider extends ContentProvider {

    public static final int COURSE = 100;
    public static final int AUTHOR = 200;
    public static final int COURSE_TAG = 300;
    public static final int COURSE_CHAPTER = 400;
    public static final int COURSE_CATEGORY = 500;
    public static final int COURSE_TODOLIST = 600;
    public static final int COURSE_TODOITEM = 700;

    private static final String sCourseTitleSelection = CoursesContract.CourseEntry.COLUMN_TITLE + " = ?";
    private static final String sAuthorNameSelection = CoursesContract.AuthorEntry.COLUMN_AUTHOR_NAME + " = ?";

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private CourseDBHelper mCourseDBHelper;

    @Override
    public boolean onCreate() {
        mCourseDBHelper = new CourseDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor queryCursor;

        int matchUri = sUriMatcher.match(uri);
        switch (matchUri) {
            case COURSE:
                String courseTitle = CoursesContract.CourseEntry.getTitleFromParam(uri);
                if (!TextUtils.isEmpty(courseTitle)) {
                    selection = sCourseTitleSelection;
                    selectionArgs = new String[]{courseTitle};
                }
                queryCursor = mCourseDBHelper.getReadableDatabase().query(CoursesContract.CourseEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            case AUTHOR:
                String authorName = CoursesContract.AuthorEntry.getAuthorNameFromParam(uri);
                if (!TextUtils.isEmpty(authorName)) {
                    selection = sAuthorNameSelection;
                    selectionArgs = new String[]{authorName};
                }
                queryCursor = mCourseDBHelper.getReadableDatabase().query(CoursesContract.AuthorEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        Context context = getContext();
        if (context != null) {
            queryCursor.setNotificationUri(context.getContentResolver(), uri);
        }

        return queryCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int matchUri = sUriMatcher.match(uri);
        switch (matchUri) {
            case COURSE:
                return CoursesContract.CourseEntry.DIR_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mCourseDBHelper.getWritableDatabase();
        final int matchUri = sUriMatcher.match(uri);
        Uri insertedUri = null;

        switch (matchUri) {
            case COURSE: {
                long _id = db.insert(CoursesContract.CourseEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    insertedUri = CoursesContract.CourseEntry.buildCourseUri(_id);
                } else {
                    // throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case AUTHOR: {
                long _id = db.insert(CoursesContract.AuthorEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    insertedUri = CoursesContract.AuthorEntry.buildAuthorUri(_id);
                } else {
                    // throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case COURSE_CATEGORY: {
                long _id = db.insert(CoursesContract.CourseCategoryEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    insertedUri = CoursesContract.CourseCategoryEntry.buildCourseCategoryUri(_id);
                } else {
                    // throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri from insert method : " + uri);
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mCourseDBHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int insertedCount = 0;

        try {
            db.beginTransaction();
            for (ContentValues cv : values) {
                long _id = db.insert(tableName, null, cv);
                if (_id > 0) {
                    insertedCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedCount;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mCourseDBHelper.getWritableDatabase();
        int rowDeleted;
        String tableName = getTableName(uri);

        rowDeleted = db.delete(tableName, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowDeleted > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mCourseDBHelper.getWritableDatabase();
        int rowUpdated;
        String tableName = getTableName(uri);

        rowUpdated = db.update(tableName, contentValues, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowUpdated > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_COURSES, COURSE);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_AUTHOR, AUTHOR);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_COURSE_TAGS, COURSE_TAG);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_COURSE_CHAPTERS, COURSE_CHAPTER);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_COURSE_CATEGORIES, COURSE_CATEGORY);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_COURSE_TODOLIST, COURSE_TODOLIST);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_COURSE_TODOITEM, COURSE_TODOITEM);

        return uriMatcher;
    }

    private String getTableName(Uri uri) {
        final int matchUri = sUriMatcher.match(uri);

        switch (matchUri) {
            case COURSE:
                return CoursesContract.CourseEntry.TABLE_NAME;
            case AUTHOR:
                return CoursesContract.AuthorEntry.TABLE_NAME;
            case COURSE_TAG:
                return CoursesContract.CourseTagEntry.TABLE_NAME;
            case COURSE_CHAPTER:
                return CoursesContract.ChapterEntry.TABLE_NAME;
            case COURSE_CATEGORY:
                return CoursesContract.CourseCategoryEntry.TABLE_NAME;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }
}
