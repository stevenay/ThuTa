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
 * Created by NayLinAung on 9/29/2016.
 */
public class TodoListVO {

    @SerializedName("to_do_list_id")
    private String todoListId;

    @SerializedName("card_id")
    private String cardId;

    @SerializedName("title")
    private String title;

    @SerializedName("items")
    private List<TodoItemVO> todoItems;

    private boolean isFinishAccess = false;

    public String getTodoListId() {
        return todoListId;
    }

    public void setTodoListId(String todoListId) {
        this.todoListId = todoListId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TodoItemVO> getTodoItems() {
        return todoItems;
    }

    public void setTodoItems(List<TodoItemVO> todoItems) {
        this.todoItems = todoItems;
    }

    public boolean isFinishAccess() {
        return isFinishAccess;
    }

    public void setFinishAccess(boolean finishAccess) {
        isFinishAccess = finishAccess;
    }

    public static void saveTodoList(String courseTitle, TodoListVO todoList) {
        ContentValues cv = new ContentValues();
        cv.put(CoursesContract.TodoListEntry.COLUMN_TODO_LIST_ID, todoList.getTodoListId());
        cv.put(CoursesContract.TodoListEntry.COLUMN_COURSE_TITLE, courseTitle);
        cv.put(CoursesContract.TodoListEntry.COLUMN_CARD_ID, todoList.getCardId());
        cv.put(CoursesContract.TodoListEntry.COLUMN_TITLE, todoList.getTitle());

        TodoItemVO.saveTodoItems(todoList.getTodoListId(), todoList.getTodoItems());

        Context context = InteractiveTrainingApp.getContext();
        Uri insertedRow = context.getContentResolver().insert(CoursesContract.TodoListEntry.CONTENT_URI, cv);

        if (insertedRow != null)
            Log.d(InteractiveTrainingApp.TAG, "OneRow inserted into todolist table : " + insertedRow.toString());
    }

    public static TodoListVO parseFromCursor(Cursor data) {
        TodoListVO todoList = new TodoListVO();

        todoList.title = data.getString(data.getColumnIndex(CoursesContract.TodoListEntry.COLUMN_TITLE));
        todoList.cardId = data.getString(data.getColumnIndex(CoursesContract.TodoListEntry.COLUMN_CARD_ID));
        todoList.todoListId = data.getString(data.getColumnIndex(CoursesContract.TodoListEntry.COLUMN_TODO_LIST_ID));

        return todoList;
    }

    public static List<TodoListVO> loadTodoListbyCourseTitle(String courseTitle) {
        Context context = InteractiveTrainingApp.getContext();
        List<TodoListVO> todoLists = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(CoursesContract.TodoListEntry.buildTodoListWithCourseTitle(courseTitle),
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                TodoListVO todoListVO = TodoListVO.parseFromCursor(cursor);

                Log.d(InteractiveTrainingApp.TAG, "loadTodoListsByCourseTitle " + todoListVO.getTitle());

                todoLists.add(todoListVO);
            } while (cursor.moveToNext());
        }

        return todoLists;
    }

}
