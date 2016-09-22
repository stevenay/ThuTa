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
import com.padc.interactive_training.adapters.DiscussionAdapter;
import com.padc.interactive_training.data.vos.DiscussionVO;
import com.padc.interactive_training.views.holders.DiscussionViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscussionListFragment extends Fragment {

    @BindView(R.id.rv_discussion_list)
    RecyclerView rvDiscussionList;

    private DiscussionAdapter discussionAdapter;
    private DiscussionViewHolder.ControllerDiscussionItem controllerDiscussionItem;

    public static DiscussionListFragment newInstance() {
        DiscussionListFragment fragment = new DiscussionListFragment();
        return fragment;
    }

    public DiscussionListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controllerDiscussionItem = (DiscussionViewHolder.ControllerDiscussionItem) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discussion_list, container, false);
        ButterKnife.bind(this, view);

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

}
