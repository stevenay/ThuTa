package com.padc.interactive_training.data.models;

import com.padc.interactive_training.data.vos.ArticleVO;
import com.padc.interactive_training.data.vos.CourseVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NayLinAung on 10/1/2016.
 */
public class ArticleModel extends BaseModel {

    public static final String BROADCAST_DATA_LOADED = "BROADCAST_DATA_LOADED";

    private static ArticleModel objInstance;
    private List<ArticleVO> mArticleList;

    private ArticleModel() {
        super();
        mArticleList = new ArrayList<>();
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

    public void notifyArticlesLoaded(List<ArticleVO> articleList) {
        //Notify that the data is ready - using LocalBroadcast
        mArticleList = articleList;

        //keep the data in persistent layer.
        ArticleVO.saveArticles(mArticleList);
    }

    public void notifyErrorInLoadingArticles(String message) {

    }
}
