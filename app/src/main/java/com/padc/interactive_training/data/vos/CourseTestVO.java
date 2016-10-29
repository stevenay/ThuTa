package com.padc.interactive_training.data.vos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NayLinAung on 10/29/2016.
 */

public class CourseTestVO {

    @SerializedName("course_test_id")
    private String testId;

    @SerializedName("chapter_id")
    private String chapterId;

    @SerializedName("test_type")
    private String testType;

    @SerializedName("allowance_time")
    private int allowanceTime;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public int getAllowanceTime() {
        return allowanceTime;
    }

    public void setAllowanceTime(int allowanceTime) {
        this.allowanceTime = allowanceTime;
    }
}
