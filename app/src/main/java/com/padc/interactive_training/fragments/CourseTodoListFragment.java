package com.padc.interactive_training.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.CourseTodoAdapter;
import com.padc.interactive_training.adapters.TodosHeadersAdapter;
import com.padc.interactive_training.data.vos.TodoItemVO;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

// This fragment is for todolists shown in the Course Detail Activity
public class CourseTodoListFragment extends Fragment {

    @BindView(R.id.rv_todo_list)
    RecyclerView rvTodoList;

    private TodosHeadersAdapter mAdapter;
    private ArrayList<String> mDataSet;
    private List<TodoItemVO> mTodoItems;

    public static CourseTodoListFragment newInstance() {
        CourseTodoListFragment fragment = new CourseTodoListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_todo_list, container, false);
        ButterKnife.bind(this, view);

        // Item Decorator:
        rvTodoList.setItemAnimator(new FadeInLeftAnimator());

        // Adapter
        mAdapter = new TodosHeadersAdapter(mTodoItems);
        mAdapter.setMode(Attributes.Mode.Single);
        rvTodoList.setAdapter(mTodoItems);

        // Layout Managers:
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvTodoList.setLayoutManager(layoutManager);

        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
        rvTodoList.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
        // rvTodoList.addItemDecoration(new DividerDecoration(getContext()));

        // Add touch listeners
        StickyRecyclerHeadersTouchListener touchListener =
                new StickyRecyclerHeadersTouchListener(rvTodoList, headersDecor);
        touchListener.setOnHeaderClickListener(
                new StickyRecyclerHeadersTouchListener.OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(View header, int position, long headerId) {
                        Toast.makeText(getContext(), "Header position: " + position + ", id: " + headerId,
                                Toast.LENGTH_SHORT).show();
                    }
                });
        rvTodoList.addOnItemTouchListener(touchListener);
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });

        return view;
    }
}
