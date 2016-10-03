package com.padc.interactive_training.data.persistence;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
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
    public static final int LESSON_CARD = 500;
    public static final int COURSE_DISCUSSION = 600;
    public static final int COURSE_REPLY = 700;
    public static final int COURSE_CATEGORY = 800;
    public static final int COURSE_TODOLIST = 900;
    public static final int COURSE_TODOITEM = 1000;
    public static final int USER = 1100;
    public static final int ARTICLE = 1200;


    private static final String sCourseTitleSelection = CoursesContract.CourseEntry.COLUMN_TITLE + " = ?";
    private static final String sAuthorNameSelection = CoursesContract.AuthorEntry.COLUMN_AUTHOR_NAME + " = ?";
    private static final String sChapterSelection = CoursesContract.ChapterEntry.COLUMN_COURSE_TITLE + " = ?";
    private static final String sDiscussionSelection = CoursesContract.DiscussionEntry.COLUMN_COURSE_TITLE + " = ?";
    private static final String sReplySelection = CoursesContract.ReplyEntry.COLUMN_DISCUSSION_ID + " = ?";
    private static final String sLessonCardSelection = CoursesContract.LessonCardEntry.COLUMN_CHAPTER_ID + " = ?";
    private static final String sLessonCardSelectionWithCourseTitle = CoursesContract.LessonCardEntry.COLUMN_COURSE_TITLE + " = ?";
    private static final String sUserSelection = CoursesContract.UserEntry.COLUMN_USER_ID + " = ?";
    private static final String sTodoListSelection = CoursesContract.TodoListEntry.COLUMN_COURSE_TITLE + " = ?";
    private static final String sTodoItemSelection = CoursesContract.TodoItemEntry.COLUMN_TODO_LIST_ID + " = ?";
    private static final String sArticleSelection = CoursesContract.ArticleEntry.COLUMN_ARTICLE_ID + " = ?";

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private CourseDBHelper mCourseDBHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_COURSES, COURSE);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_AUTHOR, AUTHOR);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_COURSE_TAGS, COURSE_TAG);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_COURSE_CHAPTERS, COURSE_CHAPTER);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_COURSE_DISCUSSIONS, COURSE_DISCUSSION);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_COURSE_REPLIES, COURSE_REPLY);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_LESSON_CARDS, LESSON_CARD);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_COURSE_CATEGORIES, COURSE_CATEGORY);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_COURSE_TODOLIST, COURSE_TODOLIST);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_COURSE_TODOITEM, COURSE_TODOITEM);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_USER, USER);
        uriMatcher.addURI(CoursesContract.CONTENT_AUTHORITY, CoursesContract.PATH_ARTICLES, ARTICLE);

        return uriMatcher;
    }

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
            case COURSE_CHAPTER:
                String chapterCourseTitle = CoursesContract.ChapterEntry.getCourseTitleFromParam(uri);
                if (!TextUtils.isEmpty(chapterCourseTitle)) {
                    selection = sChapterSelection;
                    selectionArgs = new String[]{chapterCourseTitle};
                }
                queryCursor = mCourseDBHelper.getReadableDatabase().query(CoursesContract.ChapterEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            case LESSON_CARD:
                String chapterId = CoursesContract.LessonCardEntry.getChapterIdFromParam(uri);
                String cardCourseTitle = CoursesContract.LessonCardEntry.getCourseTitleFromParam(uri);

                if (!TextUtils.isEmpty(chapterId)) {
                    selection = sLessonCardSelection;
                    selectionArgs = new String[]{chapterId};
                } else if (!TextUtils.isEmpty(cardCourseTitle)) {
                    selection = sLessonCardSelectionWithCourseTitle;
                    selectionArgs = new String[]{cardCourseTitle};
                }

                queryCursor = mCourseDBHelper.getReadableDatabase().query(CoursesContract.LessonCardEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            case COURSE_DISCUSSION:
                String discussCourseTitle = CoursesContract.DiscussionEntry.getCourseTitleFromParam(uri);
                if (!TextUtils.isEmpty(discussCourseTitle)) {
                    selection = sDiscussionSelection;
                    selectionArgs = new String[]{discussCourseTitle};
                }
                queryCursor = mCourseDBHelper.getReadableDatabase().query(CoursesContract.DiscussionEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            case COURSE_REPLY:
                String discussionId = CoursesContract.ReplyEntry.getDiscussionIdFromParam(uri);
                if (!TextUtils.isEmpty(discussionId)) {
                    selection = sReplySelection;
                    selectionArgs = new String[]{discussionId};
                }
                queryCursor = mCourseDBHelper.getReadableDatabase().query(CoursesContract.ReplyEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            case USER:
                String userId = CoursesContract.UserEntry.getUserIdFromParam(uri);
                if (!TextUtils.isEmpty(userId)) {
                    selection = sUserSelection;
                    selectionArgs = new String[]{userId};
                }
                queryCursor = mCourseDBHelper.getReadableDatabase().query(CoursesContract.UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            case COURSE_TODOLIST:
                String todoCourseTitle = CoursesContract.TodoListEntry.getCourseTitleFromParam(uri);
                if (!TextUtils.isEmpty(todoCourseTitle)) {
                    selection = sTodoListSelection;
                    selectionArgs = new String[]{todoCourseTitle};
                }
                queryCursor = mCourseDBHelper.getReadableDatabase().query(CoursesContract.TodoListEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            case COURSE_TODOITEM:
                String todoListId = CoursesContract.TodoItemEntry.getTodoListIdFromParam(uri);
                if (!TextUtils.isEmpty(todoListId)) {
                    selection = sTodoItemSelection;
                    selectionArgs = new String[]{todoListId};
                }
                queryCursor = mCourseDBHelper.getReadableDatabase().query(CoursesContract.TodoItemEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            case ARTICLE:
                String articleId = CoursesContract.ArticleEntry.getArticleIdFromParam(uri);
                if (!TextUtils.isEmpty(articleId)) {
                    selection = sArticleSelection;
                    selectionArgs = new String[]{articleId};
                }
                queryCursor = mCourseDBHelper.getReadableDatabase().query(CoursesContract.ArticleEntry.TABLE_NAME,
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
            case COURSE_TODOLIST: {
                long _id = db.insert(CoursesContract.TodoListEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    insertedUri = CoursesContract.TodoListEntry.buildTodoListUri(_id);
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
            case LESSON_CARD:
                return CoursesContract.LessonCardEntry.TABLE_NAME;
            case COURSE_CATEGORY:
                return CoursesContract.CourseCategoryEntry.TABLE_NAME;
            case COURSE_DISCUSSION:
                return CoursesContract.DiscussionEntry.TABLE_NAME;
            case COURSE_REPLY:
                return CoursesContract.ReplyEntry.TABLE_NAME;
            case COURSE_TODOLIST:
                return CoursesContract.TodoListEntry.TABLE_NAME;
            case COURSE_TODOITEM:
                return CoursesContract.TodoItemEntry.TABLE_NAME;
            case USER:
                return CoursesContract.UserEntry.TABLE_NAME;
            case ARTICLE:
                return CoursesContract.ArticleEntry.TABLE_NAME;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }
}