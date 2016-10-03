package com.padc.interactive_training.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.CourseTodoAdapter;
import com.padc.interactive_training.adapters.TodosHeadersAdapter;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.persistence.CoursesContract;
import com.padc.interactive_training.data.vos.DiscussionVO;
import com.padc.interactive_training.data.vos.ReplyVO;
import com.padc.interactive_training.data.vos.TodoItemVO;
import com.padc.interactive_training.data.vos.TodoListVO;
import com.padc.interactive_training.data.vos.UserVO;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;
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
public class CourseTodoListFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_todo_list)
    RecyclerView rvTodoList;

    private TodosHeadersAdapter mAdapter;
    private ArrayList<String> mDataSet;
    private List<TodoItemVO> mTodoItems;

    private static final String BK_COURSE_TITLE = "BK_COURSE_TITLE";
    private String mCourseTitle;

    public static CourseTodoListFragment newInstance(String courseTitle) {
        CourseTodoListFragment fragment = new CourseTodoListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BK_COURSE_TITLE, courseTitle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(InteractiveTrainingConstants.TODO_LIST_LOADER, null, this);
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

        Bundle bundle = this.getArguments();
        mCourseTitle = bundle.getString(BK_COURSE_TITLE, "Course title");

        bindTodoListData();

        return view;
    }

    private void bindTodoListData() {
        // Item Decorator:
        rvTodoList.setItemAnimator(new FadeInLeftAnimator());

        // Adapter
        mAdapter = new TodosHeadersAdapter(new ArrayList<TodoItemVO>());
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
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                CoursesContract.TodoListEntry.buildTodoListWithCourseTitle(mCourseTitle),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<TodoListVO> todoList;

        if (data != null && data.moveToFirst()) {
            todoList = new ArrayList<>();

            do {
                TodoListVO list = TodoListVO.parseFromCursor(data);
                list.setTodoItems(TodoItemVO.loadItemsbyListId(list.getTodoListId(), list.getTitle()));
                todoList.add(list);
            } while (data.moveToNext());

            CourseModel.getInstance().setTodoListData(todoList);
            Log.d(InteractiveTrainingApp.TAG, "Retrieved todoLists DESC : " + todoList.size());

            mAdapter.setNewData(todoList.get(0).getTodoItems());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
