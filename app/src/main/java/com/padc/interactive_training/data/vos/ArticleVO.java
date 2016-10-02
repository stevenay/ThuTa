package com.padc.interactive_training.data.vos;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.persistence.CoursesContract;
import java.util.List;

/**
 * Created by htoomt on 9/18/2016.
 */
public class ArticleVO {

    //region variable declaration
    @SerializedName("article_id")
    private int articleId;

    @SerializedName("published_date_time")
    private String publishedDateTime;

    @SerializedName("author")
    private String author;

    @SerializedName("img_url1")
    private String imageUrl1;

    @SerializedName("img_url2")
    private String imageUrl2;

    @SerializedName("status")
    private String status;

    @SerializedName("article_title")
    private String articleTitle;

    @SerializedName("intro_content")
    private String introContent;

    @SerializedName("first_heading")
    private String firstHeading;

    @SerializedName("first_heading_content")
    private String firstHeadingContent;

    @SerializedName("second_heading")
    private String secondHeading;

    @SerializedName("second_heading_content")
    private String secondHeadingContent;

    @SerializedName("third_heading")
    private String thirdHeading;

    @SerializedName("third_heading_content")
    private String thirdHeadingContent;
    //endregion


    public int getArticleId() {
        return articleId;
    }

    public String getPublishedDateTime() {
        return publishedDateTime;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public String getStatus() {
        return status;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getIntroContent() {
        return introContent;
    }

    public String getFirstHeading() {
        return firstHeading;
    }

    public String getFirstHeadingContent() {
        return firstHeadingContent;
    }

    public String getSecondHeading() {
        return secondHeading;
    }

    public String getSecondHeadingContent() {
        return secondHeadingContent;
    }

    public String getThirdHeading() {
        return thirdHeading;
    }

    public String getThirdHeadingContent() {
        return thirdHeadingContent;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public void setPublishedDateTime(String publishedDateTime) {
        this.publishedDateTime = publishedDateTime;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public void setImageUrl2(String imageURL2) {
        this.imageUrl2 = imageURL2;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public void setIntroContent(String introContent) {
        this.introContent = introContent;
    }

    public void setFirstHeading(String firstHeading) {
        this.firstHeading = firstHeading;
    }

    public void setFirstHeadingContent(String firstHeadingContent) {
        this.firstHeadingContent = firstHeadingContent;
    }

    public void setSecondHeading(String secondHeading) {
        this.secondHeading = secondHeading;
    }

    public void setSecondHeadingContent(String secondHeadingContent) {
        this.secondHeadingContent = secondHeadingContent;
    }

    public void setThirdHeading(String thirdHeading) {
        this.thirdHeading = thirdHeading;
    }

    public void setThirdHeadingContent(String thirdHeadingContent) {
        this.thirdHeadingContent = thirdHeadingContent;
    }

    public static void saveArticles(List<ArticleVO> articles) {
        Log.d(InteractiveTrainingApp.TAG, "Method: article. Loaded article: " + articles.size());
        ContentValues[] articleCVs = new ContentValues[articles.size()];

        for (int index = 0; index < articles.size(); index++) {
            ArticleVO article = articles.get(index);
            articleCVs[index] = article.parseToContentValues();
            Log.d(InteractiveTrainingApp.TAG, "Method: saveArticle. Article Title: " + article.getArticleTitle());
        }

        Context context = InteractiveTrainingApp.getContext();
        int insertCount = context.getContentResolver().bulkInsert(CoursesContract.ArticleEntry.CONTENT_URI, articleCVs);

        Log.d(InteractiveTrainingApp.TAG, "Bulk inserted into article table : " + insertCount);
    }

    public ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(CoursesContract.ArticleEntry.COLUMN_ARTICLE_ID, articleId);
        cv.put(CoursesContract.ArticleEntry.COLUMN_ARTICLE_TITLE, articleTitle);
        cv.put(CoursesContract.ArticleEntry.COLUMN_PUBLISHED_DATE_TIME, publishedDateTime);
        cv.put(CoursesContract.ArticleEntry.COLUMN_AUTHOR, author);
        cv.put(CoursesContract.ArticleEntry.COLUMN_IMG_URL1, imageUrl1);
        cv.put(CoursesContract.ArticleEntry.COLUMN_IMG_URL2, imageUrl2);
        cv.put(CoursesContract.ArticleEntry.COLUMN_STATUS, status);
        cv.put(CoursesContract.ArticleEntry.COLUMN_INTRO_CONTENT, introContent);
        cv.put(CoursesContract.ArticleEntry.COLUMN_FIRST_HEADING, firstHeading);
        cv.put(CoursesContract.ArticleEntry.COLUMN_FIRST_HEADING_CONTENT, firstHeadingContent);
        cv.put(CoursesContract.ArticleEntry.COLUMN_SECOND_HEADING, secondHeading);
        cv.put(CoursesContract.ArticleEntry.COLUMN_SECOND_HEADING_CONTENT, secondHeadingContent);
        cv.put(CoursesContract.ArticleEntry.COLUMN_THIRD_HEADING, thirdHeading);
        cv.put(CoursesContract.ArticleEntry.COLUMN_THIRD_HEADING_CONTENT, thirdHeadingContent);
        return cv;
    }
    public static ArticleVO parseFromCursor(Cursor data) {
        ArticleVO article = new ArticleVO();
        article.articleId = data.getInt(data.getColumnIndex(CoursesContract.ArticleEntry.COLUMN_ARTICLE_ID));
        article.articleTitle = data.getString(data.getColumnIndex(CoursesContract.ArticleEntry.COLUMN_ARTICLE_TITLE));
        article.publishedDateTime = data.getString(data.getColumnIndex(CoursesContract.ArticleEntry.COLUMN_PUBLISHED_DATE_TIME));
        article.author = data.getString(data.getColumnIndex(CoursesContract.ArticleEntry.COLUMN_AUTHOR));
        article.imageUrl1 = data.getString(data.getColumnIndex(CoursesContract.ArticleEntry.COLUMN_IMG_URL1));
        article.imageUrl2 = data.getString(data.getColumnIndex(CoursesContract.ArticleEntry.COLUMN_IMG_URL2));
        article.status = data.getString(data.getColumnIndex(CoursesContract.ArticleEntry.COLUMN_STATUS));
        article.introContent = data.getString(data.getColumnIndex(CoursesContract.ArticleEntry.COLUMN_INTRO_CONTENT));
        article.firstHeading = data.getString(data.getColumnIndex(CoursesContract.ArticleEntry.COLUMN_FIRST_HEADING));
        article.firstHeadingContent = data.getString(data.getColumnIndex(CoursesContract.ArticleEntry.COLUMN_FIRST_HEADING_CONTENT));
        article.secondHeading = data.getString(data.getColumnIndex(CoursesContract.ArticleEntry.COLUMN_SECOND_HEADING));
        article.secondHeadingContent = data.getString(data.getColumnIndex(CoursesContract.ArticleEntry.COLUMN_SECOND_HEADING_CONTENT));
        article.thirdHeading = data.getString(data.getColumnIndex(CoursesContract.ArticleEntry.COLUMN_THIRD_HEADING));
        article.thirdHeadingContent = data.getString(data.getColumnIndex(CoursesContract.ArticleEntry.COLUMN_THIRD_HEADING_CONTENT));
        return article;
    }

}