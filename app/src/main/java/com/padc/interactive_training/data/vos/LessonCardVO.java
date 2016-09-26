package com.padc.interactive_training.data.vos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NayLinAung on 9/8/2016.
 */
public class LessonCardVO {

    private Integer chapterID;

    @SerializedName("card_image_url")
    private String lessonImageUrl;

    @SerializedName("card_description")
    private String lessonDescription;

    @SerializedName("card_order_number")
    private Integer cardOrderNumber;

    @SerializedName("bookmarked")
    private boolean bookmarked;

    public Integer getChapterID() {
        return chapterID;
    }

    public void setChapterID(Integer chapterID) {
        this.chapterID = chapterID;
    }

    public String getLessonImageUrl() {
        return lessonImageUrl;
    }

    public void setLessonImageUrl(String lessonImageUrl) {
        this.lessonImageUrl = lessonImageUrl;
    }

    public void setCardOrderNumber(Integer cardOrderNumber) {
        this.cardOrderNumber = cardOrderNumber;
    }

    public String getLessonDescription() {
        return lessonDescription;
    }

    public void setLessonDescription(String lessonDescription) {
        this.lessonDescription = lessonDescription;
    }

    public int getCardOrderNumber() {
        return cardOrderNumber;
    }

    public void setCardOrderNumber(int cardOrderNumber) {
        this.cardOrderNumber = cardOrderNumber;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }
}
