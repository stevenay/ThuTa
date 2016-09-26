package com.padc.interactive_training.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;

import com.daimajia.swipe.util.Attributes;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.TodoAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

public class TodoListActivity extends AppCompatActivity {

    @BindView(R.id.rv_todo_list)
    RecyclerView rvTodoList;

    private RecyclerView.Adapter mAdapter;
    private ArrayList<String> mDataSet;

    private static final String IE_COURSE_ID = "IE_COURSE_ID";

    public static Intent newIntent(String courseID) {
        Intent intent = new Intent(InteractiveTrainingApp.getContext(), TodoListActivity.class);
        intent.putExtra(IE_COURSE_ID, courseID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this, this);

        // Layout Managers:
        rvTodoList.setLayoutManager(new LinearLayoutManager(this));

        // Item Decorator:
//        rvTodoList.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        rvTodoList.setItemAnimator(new FadeInLeftAnimator());

        // Adapter:
        String[] adapterData = new String[]{
                "Write down my skills/expertise in this field.",
                "Write down how I'm different from other experts in the field.",
                "Write down how others will benefit from my opinions and advice.",
                "Define my personal branding target audience.",
                "Write down my target audience's interests that are related to my field.",
                "Create a personal branding website.",
                "Write a short website bio and a shorter social media bio.",
                "Choose a universal profile photo."};
        mDataSet = new ArrayList<String>(Arrays.asList(adapterData));
        mAdapter = new TodoAdapter(mDataSet);

        rvTodoList.setAdapter(mAdapter);

        /* Listeners */
        rvTodoList.setOnScrollListener(onScrollListener);
    }

    @OnClick(R.id.btn_back)
    public void onbtnBackPressed(ImageButton view) { this.onBackPressed(); }

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
