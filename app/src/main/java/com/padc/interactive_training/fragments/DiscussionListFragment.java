package com.padc.interactive_training.fragments;


import android.content.Context;
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

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.DiscussionAdapter;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.persistence.CoursesContract;
import com.padc.interactive_training.data.vos.ChapterVO;
import com.padc.interactive_training.data.vos.DiscussionVO;
import com.padc.interactive_training.data.vos.LessonCardVO;
import com.padc.interactive_training.data.vos.ReplyVO;
import com.padc.interactive_training.data.vos.UserVO;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;
import com.padc.interactive_training.views.holders.DiscussionViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscussionListFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_discussion_list)
    RecyclerView rvDiscussionList;

    private static final String BK_COURSE_TITLE = "BK_COURSE_TITLE";
    private String mCourseTitle;

    private DiscussionAdapter discussionAdapter;
    private DiscussionViewHolder.ControllerDiscussionItem controllerDiscussionItem;

    public static DiscussionListFragment newInstance(String courseTitle) {
        DiscussionListFragment fragment = new DiscussionListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BK_COURSE_TITLE, courseTitle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controllerDiscussionItem = (DiscussionViewHolder.ControllerDiscussionItem) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(InteractiveTrainingConstants.DISCUSSION_LIST_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discussion_list, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        mCourseTitle = bundle.getString(BK_COURSE_TITLE, "Course title");

        bindDiscussionListData();

        return view;
    }

    private void bindDiscussionListData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvDiscussionList.setLayoutManager(linearLayoutManager);

        discussionAdapter = new DiscussionAdapter(new ArrayList<DiscussionVO>(), controllerDiscussionItem);
        rvDiscussionList.setAdapter(discussionAdapter);
    }

    //region LoaderPattern
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                CoursesContract.DiscussionEntry.buildDiscussionUriWithCourseTitle(mCourseTitle),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<DiscussionVO> discussionList;

        if (data != null && data.moveToFirst()) {
            discussionList = new ArrayList<>();

            do {
                DiscussionVO discussion = DiscussionVO.parseFromCursor(data);
                discussion.setReplies(ReplyVO.loadRepliesbyDiscussionId(discussion.getDiscussionId()));
                discussion.setUser(UserVO.loadUserbyUserId(discussion.getUserId()));
                discussionList.add(discussion);
            } while (data.moveToNext());

            CourseModel.getInstance().setDiscussionListData(discussionList);
            Log.d(InteractiveTrainingApp.TAG, "Retrieved discussion DESC : " + discussionList.size());

            discussionAdapter.setNewData(discussionList);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    //endregion
}
