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
import com.padc.interactive_training.data.vos.ArticleVO;
import com.padc.interactive_training.utils.DateTimeUtils;
import com.padc.interactive_training.views.holders.ArticleViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArticleListFragment extends Fragment {

    //region variable declaration
    private View view;
    private ArticleAdapter articleAdapter;
    private ArticleViewHolder.ControllerArticleItem controllerArticleItem;

    @BindView(R.id.rv_articles)
    RecyclerView rvArticle;




    //endregion

    public static ArticleListFragment newInstance(){
        ArticleListFragment fragment = new ArticleListFragment(); //ALFrag = Article List Fragment
        return fragment;
    }

    public ArticleListFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controllerArticleItem = (ArticleViewHolder.ControllerArticleItem) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article_list, container, false);
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
        rvArticle.setLayoutManager(linearLayoutManager);
        articleAdapter = new ArticleAdapter(prepareSampleArticleList(), getContext(),controllerArticleItem);
        rvArticle.setAdapter(articleAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
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

    public List<ArticleVO> prepareSampleArticleList(){
        List<ArticleVO> articleList = new ArrayList<>();

        ArticleVO articleSample1 = new ArticleVO();
        articleSample1.setTitle("Article Title 1");
        articleSample1.setBriefDescription(getString(R.string.lorem_ipsum_one_third));
        articleSample1.setPublishedDate(DateTimeUtils.parseStringToDate("2015-08-21"));
        articleSample1.setCategoryName("Driving Manual");
        articleSample1.setImageURL(R.drawable.tech_android_wallpaper);
        articleList.add(articleSample1);

        ArticleVO articleSample2 = new ArticleVO();
        articleSample2.setTitle("Article Title 2");
        articleSample2.setBriefDescription(getString(R.string.lorem_ipsum_one_third));
        articleSample2.setPublishedDate(DateTimeUtils.parseStringToDate("2015-08-21"));
        articleSample2.setCategoryName("Driving Manual");
        articleSample2.setImageURL(R.drawable.gento_cow);
        articleList.add(articleSample2);

        ArticleVO articleSample3 = new ArticleVO();
        articleSample3.setTitle("Article Title 3");
        articleSample3.setBriefDescription(getString(R.string.lorem_ipsum_one_third));
        articleSample3.setPublishedDate(DateTimeUtils.parseStringToDate("2015-08-21"));
        articleSample3.setCategoryName("Driving Manual");
        articleSample3.setImageURL(R.drawable.otto_blue);
        articleList.add(articleSample3);

        return articleList;
    }
}
