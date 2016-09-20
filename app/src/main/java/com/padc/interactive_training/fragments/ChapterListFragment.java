package com.padc.interactive_training.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.ChapterAdapter;
import com.padc.interactive_training.data.vos.ChapterVO;
import com.padc.interactive_training.views.holders.ChapterViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChapterListFragment extends Fragment {

    @BindView(R.id.rv_chapter_list)
    RecyclerView rvChapterList;

    private ChapterAdapter chapterAdapter;
    private ChapterViewHolder.ControllerChapterItem controllerChapterItem;

    public static ChapterListFragment newInstance() {
        ChapterListFragment fragment = new ChapterListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controllerChapterItem = (ChapterViewHolder.ControllerChapterItem) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapter_list, container, false);
        ButterKnife.bind(this, view);

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

        chapterAdapter = new ChapterAdapter(prepareSampleChapterList(), controllerChapterItem);
        rvChapterList.setAdapter(chapterAdapter);
    }

    public List<ChapterVO> prepareSampleChapterList() {
        List<ChapterVO> chapterList = new ArrayList<>();

        ChapterVO chapterOne = new ChapterVO();
        chapterOne.setChapterNumber(1);
        chapterOne.setTitle("Wear broad-spectrum sunscreen.");
        chapterOne.setChapterBrief("It's not enough to wear sunscreen. You need to wear the right kind of sunscreen and keep reapplying.");
        chapterOne.setLessonCount(6);
        chapterOne.setDurationInMins(4);
        chapterOne.setLocked(false);
        chapterOne.setFinishedPercentage(100);
        chapterList.add(chapterOne);

        ChapterVO chapterTwo = new ChapterVO();
        chapterTwo.setChapterNumber(2);
        chapterTwo.setTitle("Put on protective clothing.");
        chapterTwo.setChapterBrief("It's not enough to wear sunscreen. You need to wear the right kind of sunscreen and keep reapplying.");
        chapterTwo.setLessonCount(6);
        chapterTwo.setDurationInMins(4);
        chapterTwo.setLocked(false);
        chapterTwo.setFinishedPercentage(40);
        chapterList.add(chapterTwo);

        ChapterVO chapterThree = new ChapterVO();
        chapterThree.setChapterNumber(3);
        chapterThree.setTitle("Use UV-blocking sunglasses.");
        chapterThree.setChapterBrief("It's not enough to wear sunscreen. You need to wear the right kind of sunscreen and keep reapplying.");
        chapterThree.setLessonCount(6);
        chapterThree.setDurationInMins(4);
        chapterThree.setLocked(false);
        chapterThree.setFinishedPercentage(0);
        chapterList.add(chapterThree);

        ChapterVO chapterFour = new ChapterVO();
        chapterFour.setChapterNumber(4);
        chapterFour.setTitle("Limiting Your Overall Exposure to UV Rays");
        chapterFour.setChapterBrief("It's not enough to wear sunscreen. You need to wear the right kind of sunscreen and keep reapplying.");
        chapterFour.setLessonCount(7);
        chapterFour.setDurationInMins(3);
        chapterFour.setLocked(true);
        chapterFour.setFinishedPercentage(0);
        chapterList.add(chapterFour);

        ChapterVO chapterFive = new ChapterVO();
        chapterFive.setChapterNumber(5);
        chapterFive.setTitle("Stay out of the sun during peak UV radiation times");
        chapterFive.setChapterBrief("It's not enough to wear sunscreen. You need to wear the right kind of sunscreen and keep reapplying.");
        chapterFive.setLessonCount(9);
        chapterFive.setDurationInMins(5);
        chapterFive.setLocked(true);
        chapterFive.setFinishedPercentage(0);
        chapterList.add(chapterFive);

        return chapterList;
    }

}
