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
import com.padc.interactive_training.views.holders.CourseTodoItemViewHolder;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

// This fragment is for todolists shown in the Course Detail Activity
public class CourseTodoListFragment extends Fragment {

    @BindView(R.id.rv_todo_list)
    RecyclerView rvTodoList;

    private TodosHeadersAdapter mAdapter;
    private ArrayList<String> mDataSet;

    public static CourseTodoListFragment newInstance() {
        CourseTodoListFragment fragment = new CourseTodoListFragment();
        return fragment;
    }

    public CourseTodoListFragment() {
        // Required empty public constructor
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
        // rvTodoList.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        rvTodoList.setItemAnimator(new FadeInLeftAnimator());

        // Adapter:
        String[] adapterData = new String[]{
                "Write down my skills/expertise in this field.",
                "Write down how I'm different from other experts in the field.",
                "Write down how others will benefit from my opinions and advice.",
                "Write down my target audience's interests that are related to my field.",
                "Write a short website bio and a shorter social media bio.",
                "Define my personal branding target audience.",
                "Define and create a personal branding website.",
                "Define and Choose a universal profile photo.",
                "Define and Choose a universal profile photo.",
                "Define and Choose a universal profile photo.",
                "Define and Choose a universal profile photo.",
                "Define and Choose a universal profile photo."};
        mDataSet = new ArrayList<String>(Arrays.asList(adapterData));

        mAdapter = new TodosHeadersAdapter(mDataSet);
        mAdapter.setMode(Attributes.Mode.Single);
        rvTodoList.setAdapter(mAdapter);

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

    private class TodosHeadersAdapter extends CourseTodoAdapter
            implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

        public TodosHeadersAdapter(ArrayList<String> objects) {
            super(objects);
        }

        @Override
        public CourseTodoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_course_todo, parent, false);
            return new CourseTodoItemViewHolder(view, this) {};
        }

        @Override
        public long getHeaderId(int position) {
            return getItem(position).charAt(0);
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_header_course_todo, parent, false);
            return new RecyclerView.ViewHolder(view) {};
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
            textView.setText("List 1: Install Java into Your Machine");
            // holder.itemView.setBackgroundColor(getRandomColor());
        }
    }
}
