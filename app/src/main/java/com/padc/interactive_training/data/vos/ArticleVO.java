package com.padc.interactive_training.data.vos;


import java.util.Date;

/**
 * Created by htoomt on 9/18/2016.
 */
public class ArticleVO {

    //region variable declaration
    private String title;
    private Date publishedDate;
    private String categoryName;
    private String briefDescription;
    private String imageURL;
    //endregion


    public String getTitle() {
        return title;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


}
