package com.padc.interactive_training.data.vos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dell on 9/2/2016.
 */
public class CourseVO {

    private String courseTitle;
    private String categoryName;
    private Integer durationInMinute;
    private String authorName;
    private String colorCode;
    private String progressColorCode;
    private String coverPhotoUrl;
    private List<ChapterVO> chapters;
    private List<DiscussionVO> discussions;

    public String getTitle() {
        return courseTitle;
    }

    public void setTitle(String title) {
        this.courseTitle = title;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getDurationInMinute() {
        return durationInMinute;
    }

    public void setDurationInMinute(Integer durationInMinute) {
        this.durationInMinute = durationInMinute;
    }

    public String getProgressColorCode() {
        return progressColorCode;
    }

    public void setProgressColorCode(String progressColorCode) {
        this.progressColorCode = progressColorCode;
    }

    public String getCoverPhotoUrl() {
        return coverPhotoUrl;
    }

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        this.coverPhotoUrl = coverPhotoUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public List<ChapterVO> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChapterVO> chapters) {
        this.chapters = chapters;
    }

    public List<DiscussionVO> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<DiscussionVO> discussions) {
        this.discussions = discussions;
    }
}
