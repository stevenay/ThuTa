package com.padc.interactive_training.data.responses;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.data.vos.ArticleVO;
import com.padc.interactive_training.data.vos.CourseVO;

import java.util.ArrayList;

/**
 * Created by NayLinAung on 10/1/2016.
 */
public class ArticleListResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("articles")
    private ArrayList<ArticleVO> articleList;

    public int getCode() { return code; }

    public String getMessage() {
        return message;
    }

    public ArrayList<ArticleVO> getArticleList() {
        return articleList;
    }
}
