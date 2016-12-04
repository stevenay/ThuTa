package com.padc.interactive_training.data.models;

import com.padc.interactive_training.data.vos.ArticleVO;
import com.padc.interactive_training.events.DataEvent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by NayLinAung on 10/1/2016.
 */

public class ArticleModel extends BaseModel {

    private static ArticleModel objInstance;

    private List<ArticleVO> articleList;

    public ArticleModel() {
        super();
        articleList = new ArrayList<>();
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

    public List<ArticleVO> getArticleList() {
        return articleList;
    }

    //region BroadcastEvents
    private void broadcastArticleLoaded() {
        EventBus.getDefault().post(new DataEvent.ArticlesDataLoadedEvent("extra-in-broadcast", articleList));
    }

    private void broadcastArticleErrorInLoading(String errorMessage) {
        EventBus.getDefault().post(new DataEvent.ArticlesErrorDataLoadedEvent(errorMessage));
    }
    //endregion

    //region Notify Callback
    public void notifyErrorInLoadingArticles(String errorMessage) {
        broadcastArticleErrorInLoading(errorMessage);
    }

    public void notifyArticlesLoaded(ArrayList<ArticleVO> articleList) {
        this.articleList = articleList;
        ArticleVO.saveArticles(articleList);
        broadcastArticleLoaded();
    }
    //endregion

    //region StoreDataonMemory
    public List<ArticleVO> getStoredArticleData() {
        return articleList;
    }

    public void setStoredArticleData(List<ArticleVO> articleList) {
        this.articleList = articleList;
    }
    //endregion
}