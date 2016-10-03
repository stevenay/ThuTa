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
import com.padc.interactive_training.adapters.ChapterAdapter;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.persistence.CoursesContract;
import com.padc.interactive_training.data.vos.ChapterVO;
import com.padc.interactive_training.data.vos.CourseVO;
import com.padc.interactive_training.data.vos.LessonCardVO;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;
import com.padc.interactive_training.views.holders.ChapterViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChapterListFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_chapter_list)
    RecyclerView rvChapterList;

    private static final String BK_COURSE_TITLE = "BK_COURSE_TITLE";
    private String mCourseTitle;

    public ChapterAdapter chapterAdapter;
    private ChapterViewHolder.ControllerChapterItem controllerChapterItem;

    public static ChapterListFragment newInstance(String courseTitle) {
        ChapterListFragment fragment = new ChapterListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BK_COURSE_TITLE, courseTitle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controllerChapterItem = (ChapterViewHolder.ControllerChapterItem) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(InteractiveTrainingConstants.CHAPTER_LIST_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapter_list, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        mCourseTitle = bundle.getString(BK_COURSE_TITLE, "Course title");

        bindChapterListData();

        return view;
    }

    private void bindChapterListData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvChapterList.setLayoutManager(linearLayoutManager);

        chapterAdapter = new ChapterAdapter(new ArrayList<ChapterVO>(), controllerChapterItem);
        rvChapterList.setAdapter(chapterAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                CoursesContract.ChapterEntry.buildChapterUriWithCourseTitle(mCourseTitle),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<ChapterVO> chapterList;

        if (data != null && data.moveToFirst()) {
            chapterList = new ArrayList<>();

            do {
                ChapterVO chapter = ChapterVO.parseFromCursor(data);
                chapter.setLessonCards(LessonCardVO.loadLessonCardsByChapterId(chapter.getChapterId()));
                chapterList.add(chapter);
            } while (data.moveToNext());

            CourseModel.getInstance().setChapterListData(chapterList);
            Log.d(InteractiveTrainingApp.TAG, "Retrieved chapters DESC : " + chapterList.size());

            chapterAdapter.setNewData(chapterList);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
