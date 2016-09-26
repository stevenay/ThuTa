package com.padc.interactive_training.data.responses;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.data.vos.CourseVO;

import java.util.ArrayList;

/**
 * Created by NayLinAung on 9/22/16.
 */
public class CourseListResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("courses")
    private ArrayList<CourseVO> courseList;

    public int getCode() { return code; }

    public String getMessage() {
        return message;
    }

    public ArrayList<CourseVO> getCourseList() {
        return courseList;
    }
}
