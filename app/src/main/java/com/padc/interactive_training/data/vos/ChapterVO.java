package com.padc.interactive_training.data.vos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NayLinAung on 9/6/2016.
 */
public class ChapterVO {

    @SerializedName("chapter_id")
    private String chapterId;

    @SerializedName("chapter_number")
    private int chapterNumber;

    @SerializedName("chapter_title")
    private String title;

    @SerializedName("chapter_brief")
    private String chapterBrief;

    @SerializedName("lesson_cards")
    private List<LessonCardVO> lessonCards;

    @SerializedName("duration")
    private Integer durationInMins;

    private Integer lessonCount;

    private Integer questionCount;

    @SerializedName("locked")
    private boolean locked;

    @SerializedName("finished_percentage")
    private Integer finishedPercentage;

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public Integer getFinishedPercentage() {
        return finishedPercentage;
    }

    public void setFinishedPercentage(Integer finishedPercentage) {
        this.finishedPercentage = finishedPercentage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(Integer lessonCount) {
        this.lessonCount = lessonCount;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public String getChapterBrief() {
        return chapterBrief;
    }

    public void setChapterBrief(String chapterBrief) {
        this.chapterBrief = chapterBrief;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Integer getDurationInMins() {
        return durationInMins;
    }

    public void setDurationInMins(Integer durationInMins) {
        this.durationInMins = durationInMins;
    }

    public List<LessonCardVO> getLessonCards() {
        return lessonCards;
    }

    public void setLessonCards(List<LessonCardVO> lessonCards) {
        this.lessonCards = lessonCards;
        this.lessonCount = lessonCards.size();
    }
}
