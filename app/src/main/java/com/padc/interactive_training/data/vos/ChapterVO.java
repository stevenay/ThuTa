package com.padc.interactive_training.data.vos;

/**
 * Created by Dell on 9/6/2016.
 */
public class ChapterVO {

    private int chapterNumber;
    private String title;
    private String chapterBrief;
    private Integer lessonCount;
    private Integer questionCount;
    private Integer durationInMins;
    private boolean locked;
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
}
