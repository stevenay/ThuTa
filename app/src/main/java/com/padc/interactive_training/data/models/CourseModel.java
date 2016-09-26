package com.padc.interactive_training.data.models;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.vos.CourseVO;
import com.padc.interactive_training.events.DataEvent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by NayLinAung on 9/22/2016.
 */
public class CourseModel extends BaseModel {

    public static final String BROADCAST_DATA_LOADED = "BROADCAST_DATA_LOADED";

    private static CourseModel objInstance;

    private List<CourseVO> mCourseList;

    private CourseVO mFeaturedCourse;

    private CourseModel() {
        super();
        mCourseList = new ArrayList<>();
        loadCourses();
    }

    public static CourseModel getInstance() {
        if (objInstance == null) {
            objInstance = new CourseModel();
        }
        return objInstance;
    }

    public void loadCourses() {
        dataAgent.loadCourses();
    }

    public List<CourseVO> getCourseList() {
        return mCourseList;
    }

    public CourseVO getCourseByTitle(String courseTitle) {
        for (CourseVO course : mCourseList) {
            if (course.getTitle().equals(course))
                return course;
        }

        return null;
    }

    public void notifyCoursesLoaded(List<CourseVO> courseList) {
        //Notify that the data is ready - using LocalBroadcast
        mCourseList = courseList;

        //keep the data in persistent layer.
        CourseVO.saveCourses(mCourseList);
    }

    public void notifyErrorInLoadingCourses(String message) {

    }

//    public String getRandomCourseImage() {
//        if (mAttractionList == null || mAttractionList.size() == 0) {
//            return null;
//        }
//
//        Random random = new Random();
//        int randomInt = random.nextInt(mAttractionList.size());
//
//        AttractionVO attraction = mAttractionList.get(randomInt);
//        return MyanmarAttractionsConstants.IMAGE_ROOT_DIR + attraction.getImages()[attraction.getImages().length - 1];
//    }

    private void broadcastCourseLoadedWithLocalBroadcastManager() {
        Intent intent = new Intent(BROADCAST_DATA_LOADED);
        intent.putExtra("key-for-extra", "extra-in-broadcast");
        LocalBroadcastManager.getInstance(InteractiveTrainingApp.getContext()).sendBroadcast(intent);
    }

    private void broadcastAttractionLoadedWithEventBus() {
        EventBus.getDefault().post(new DataEvent.CourseDataLoadedEvent("extra-in-broadcast", mCourseList));
    }

    public void setStoredFeaturedCourseData(CourseVO featuredCourse) {
        mFeaturedCourse = featuredCourse;
    }

    public void setStoredData(List<CourseVO> courseList) {
        mCourseList = courseList;
    }

}
