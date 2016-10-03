package com.padc.interactive_training.data.models;

import com.padc.interactive_training.data.vos.ArticleVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by htoomt on 10/1/2016.
 */

public class ArticleModel extends BaseModel {

    public static final String BROCAST_DATA_LOADED = "BROCAST_DATA_LOADED";

    private static ArticleModel objInstance;

    private List<ArticleVO> mAtricleList;

    public ArticleModel() {
        super();
        mAtricleList = new ArrayList<>();
        loadArticles();

    }

    public static ArticleModel getInstance() {
        if (objInstance == null) {
            objInstance = new ArticleModel();
        }
        return objInstance;

    }

    public void loadArticles() {
        dataAgent.loadArticles();
    }

    public void notifyErrorInLoadingArticles(String message) {

    }

    public void notifyArticlesLoaded(ArrayList<ArticleVO> articleList) {
        mAtricleList = articleList;
        ArticleVO.saveArticles(mAtricleList);
    }

    public List<ArticleVO> getStoredArticleData() {
        return mAtricleList;
    }

    public void setStoredArticleData(List<ArticleVO> articleList) {
        mAtricleList = articleList;
    }
}