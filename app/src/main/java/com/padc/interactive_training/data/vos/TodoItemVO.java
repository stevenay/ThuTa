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
 * Created by NayLinAung on 9/29/2016.
 */
public class TodoItemVO {

    @SerializedName("to-do-item-id")
    private String todoItemId;

    private String todoListId;

    private String todoListName;

    @SerializedName("description")
    private String description;

    @SerializedName("checked")
    private Boolean checked;

    public String getTodoItemId() {
        return todoItemId;
    }

    public void setTodoItemId(String todoItemId) {
        this.todoItemId = todoItemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getTodoListId() {
        return todoListId;
    }

    public void setTodoListId(String todoListId) {
        this.todoListId = todoListId;
    }

    public String getTodoListName() {
        return todoListName;
    }

    public void setTodoListName(String todoListName) {
        this.todoListName = todoListName;
    }

    public static void saveTodoItems(String todoListId, List<TodoItemVO> todoItems) {
        Log.d(InteractiveTrainingApp.TAG, "Method: TodoItem. Loaded items: " + todoItems.size());

        ContentValues[] todoItemCVs = new ContentValues[todoItems.size()];
        for (int index = 0; index < todoItems.size(); index++) {
            TodoItemVO todoItem = todoItems.get(index);
            todoItemCVs[index] = todoItem.parseToContentValues(todoListId);

            Log.d(InteractiveTrainingApp.TAG, "Method: saveTodos. Todo Description: " + todoItem.getDescription());
        }

        Context context = InteractiveTrainingApp.getContext();
        int insertCount = context.getContentResolver().bulkInsert(CoursesContract.TodoItemEntry.CONTENT_URI, todoItemCVs);

        Log.d(InteractiveTrainingApp.TAG, "Bulk inserted into todoItems table : " + insertCount);
    }

    public ContentValues parseToContentValues(String todoListId) {
        ContentValues cv = new ContentValues();

        cv.put(CoursesContract.TodoItemEntry.COLUMN_TODO_LIST_ID, todoListId);
        cv.put(CoursesContract.TodoItemEntry.COLUMN_DESCRIPTION, description);
        cv.put(CoursesContract.TodoItemEntry.COLUMN_CHECKED, checked ? 1 : 0);

        return cv;
    }

    public static TodoItemVO parseFromCursor(Cursor data) {
        TodoItemVO todoItem = new TodoItemVO();

        todoItem.todoListId = data.getString(data.getColumnIndex(CoursesContract.TodoItemEntry.COLUMN_TODO_LIST_ID));
        todoItem.description = data.getString(data.getColumnIndex(CoursesContract.TodoItemEntry.COLUMN_DESCRIPTION));
        todoItem.checked = data.getInt(data.getColumnIndex(CoursesContract.TodoItemEntry.COLUMN_CHECKED)) == 1 ? true : false;

        return todoItem;
    }

    public static List<TodoItemVO> loadItemsbyListId(String todoListId, String todoListName) {
        Context context = InteractiveTrainingApp.getContext();
        List<TodoItemVO> todoItems = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(CoursesContract.TodoItemEntry.buildTodoItemWithTodoListId(todoListId),
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                TodoItemVO todoItem = TodoItemVO.parseFromCursor(cursor);
                todoItem.setTodoListName(todoListName);
                Log.d(InteractiveTrainingApp.TAG, "Load Items by TodoListId " + todoItem.getDescription());
                todoItems.add(todoItem);
            } while (cursor.moveToNext());
        }

        return todoItems;
    }

}
