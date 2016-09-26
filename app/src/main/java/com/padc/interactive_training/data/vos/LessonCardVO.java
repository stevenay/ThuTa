package com.padc.interactive_training.data.vos;

/**
 * Created by NayLinAung on 9/8/2016.
 */
public class LessonCardVO {

    private int cardID;
    private int courseID;
    private Integer chapterID;
    private String lessonImage;
    private String lessonDescription;
    private Integer cardOrderNumber;
    private boolean bookmarked;

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public Integer getChapterID() {
        return chapterID;
    }

    public void setChapterID(Integer chapterID) {
        this.chapterID = chapterID;
    }

    public String getLessonImage() {
        return lessonImage;
    }

    public void setLessonImage(String lessonImage) {
        this.lessonImage = lessonImage;
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
