package com.padc.interactive_training.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.TodoAdapter;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.vos.TodoItemVO;
import com.padc.interactive_training.data.vos.TodoListVO;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

public class TodoListActivity extends AppCompatActivity {

    @BindView(R.id.rv_todo_list)
    RecyclerView rvTodoList;

    @BindView(R.id.tv_list_description)
    TextView tvListDescription;

    private List<TodoItemVO> mTodoItemList;

    private String mTodoListId;
    private TodoAdapter mAdapter;
    private ArrayList<String> mDataSet;

    private static final String IE_TODOLIST_ID = "IE_TODOLIST_ID";

    public static Intent newIntent(String todoListId) {
        Intent intent = new Intent(InteractiveTrainingApp.getContext(), TodoListActivity.class);
        intent.putExtra(IE_TODOLIST_ID, todoListId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this, this);

        mTodoListId = getIntent().getStringExtra(IE_TODOLIST_ID);

        // Layout Managers:
        rvTodoList.setLayoutManager(new LinearLayoutManager(this));
        rvTodoList.setItemAnimator(new FadeInLeftAnimator());

        // Adapter:
        mAdapter = new TodoAdapter(new ArrayList<TodoItemVO>());
        rvTodoList.setAdapter(mAdapter);

        /* Listeners */
        rvTodoList.setOnScrollListener(onScrollListener);

        if (!mTodoListId.isEmpty()) {
            bindData(CourseModel.getInstance().getTodoListbyListId(mTodoListId));
        }
    }

    private void bindData(TodoListVO todoList) {
        if (todoList != null) {
            tvListDescription.setText(todoList.getTitle());
            mAdapter.setNewData(todoList.getTodoItems());
        }
    }

    //region ButtonEvents
    @OnClick(R.id.btn_back)
    public void onbtnBackPressed(ImageButton view) { this.onBackPressed(); }

    @OnClick(R.id.btn_continue)
    public void onbtnContinue(Button view) { this.onBackPressed(); }
    //endregion

    /**
     * Substitute for our onScrollListener for RecyclerView
     */
    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.e("ListView", "onScrollStateChanged");
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Could hide open views here if you wanted. //
        }
    };
}
