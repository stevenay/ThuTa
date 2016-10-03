package com.padc.interactive_training.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.ArticleAdapter;
import com.padc.interactive_training.adapters.OutsourceArticleAdapter;
import com.padc.interactive_training.data.vos.OutsourceArticleVO;
import com.padc.interactive_training.views.holders.ArticleViewHolder;
import com.padc.interactive_training.views.holders.OutsourceArticleViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutsourceArticleListFragment extends Fragment {

    @BindView(R.id.rv_outsource_articles)
    RecyclerView rvOutsourceArticle;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private OutsourceArticleAdapter outsourceArticleAdapter;
    private OutsourceArticleViewHolder.ControllerArticleItem controllerArticleItem;

    public static OutsourceArticleListFragment newInstance() {
        OutsourceArticleListFragment fragment = new OutsourceArticleListFragment();
        return fragment;
    }

    public OutsourceArticleListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OutsourceArticleViewHolder.ControllerArticleItem){
            controllerArticleItem = (OutsourceArticleViewHolder.ControllerArticleItem) context;
        }
        else{
            throw new RuntimeException(context.toString()+ "must implement OutsourceArticleViewHolder.ControllerArticleItem");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_outsource_article_list, container, false);
        ButterKnife.bind(this, view);

        bindArticleListData();

        return view;
    }

    private void bindArticleListData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()){
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state){
                return 300;
            }
        };
        rvOutsourceArticle.setLayoutManager(linearLayoutManager);
        outsourceArticleAdapter = new OutsourceArticleAdapter(prepareSampleArticleList(), getContext(), controllerArticleItem);
        rvOutsourceArticle.setAdapter(outsourceArticleAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },3000); // ms
            }
        });
    }

    public List<OutsourceArticleVO> prepareSampleArticleList(){
        List<OutsourceArticleVO> articleList = new ArrayList<>();

        OutsourceArticleVO articleSample2 = new OutsourceArticleVO();
        articleSample2.setArticleTitle("Paint with friends: Tilt Brush's latest prototype creations");
        articleSample2.setOutsourceUrl("https://blog.google/products/google-vr/paint-friends-tilt-brushs-latest-prototype-creations/");
        articleSample2.setOutsourceName("blog.google.com");
        articleSample2.setAuthorName("Drew Skillman");
        articleSample2.setCategoryName("Technology");
        articleSample2.setPostDate("30 Sep 2016");
        articleSample2.setLogoUrl("google");
        articleList.add(articleSample2);

        OutsourceArticleVO articleSample1 = new OutsourceArticleVO();
        articleSample1.setArticleTitle("3 questions on cybersecurity that should be asked in the debates");
        articleSample1.setOutsourceUrl("https://medium.com/@kshortridge/3-questions-on-cybersecurity-that-should-be-asked-in-the-debates-7438fb3164a0#.zii924v90");
        articleSample1.setOutsourceName("medium.com");
        articleSample1.setAuthorName("Kelly Shortridge");
        articleSample1.setCategoryName("Technology");
        articleSample1.setPostDate("3 Oct 2016");
        articleSample1.setLogoUrl("medium");
        articleList.add(articleSample1);

        OutsourceArticleVO articleSample3 = new OutsourceArticleVO();
        articleSample3.setArticleTitle("Introducing LambCI — a serverless build system");
        articleSample3.setOutsourceUrl("https://medium.com/@hichaelmart/lambci-4c3e29d6599b#.16asa4gl5");
        articleSample3.setOutsourceName("medium.com");
        articleSample3.setAuthorName("Michael Hart");
        articleSample3.setCategoryName("Technology");
        articleSample3.setPostDate("7 Jul 2016");
        articleSample3.setLogoUrl("medium");
        articleList.add(articleSample3);

        OutsourceArticleVO articleSample4 = new OutsourceArticleVO();
        articleSample4.setArticleTitle("How to analyze a Business Process");
        articleSample4.setOutsourceUrl("http://www.wikihow.com/Analyze-a-Business-Process");
        articleSample4.setOutsourceName("wikihow.com");
        articleSample4.setAuthorName("Michael R. Lewis");
        articleSample4.setCategoryName("Finance");
        articleSample4.setPostDate("1 May 2016");
        articleSample4.setLogoUrl("wikihow");
        articleList.add(articleSample4);

        return articleList;
    }
}
