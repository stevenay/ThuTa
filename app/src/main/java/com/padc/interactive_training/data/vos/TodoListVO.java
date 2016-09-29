package com.padc.interactive_training.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.persistence.CoursesContract;

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

}
