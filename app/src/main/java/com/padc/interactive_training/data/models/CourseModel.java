package com.padc.interactive_training.data.models;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.vos.ArticleVO;
import com.padc.interactive_training.data.vos.ChapterVO;
import com.padc.interactive_training.data.vos.CourseVO;
import com.padc.interactive_training.data.vos.DiscussionVO;
import com.padc.interactive_training.data.vos.LessonCardVO;
import com.padc.interactive_training.data.vos.TodoItemVO;
import com.padc.interactive_training.data.vos.TodoListVO;
import com.padc.interactive_training.data.vos.UserVO;
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
    private List<UserVO> mUserList;
    private List<LessonCardVO> mCurrentAccessCardList;
    private List<ChapterVO> mCurrentAccessChapterList;
    private List<DiscussionVO> mCurrentAccessDiscussionList;
    private List<TodoListVO> mCurrentTodoList;
    private List<TodoItemVO> mCurrentTodoItemList;
    private List<ArticleVO> mArticleList;

    private CourseVO mFeaturedCourse;

    private CourseModel() {
        super();
        mCourseList = new ArrayList<>();
        mCurrentAccessCardList = new ArrayList<>();
        loadCourses();
        loadUsers();
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

    public void loadUsers() { dataAgent.loadUsers(); }

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

    public void notifyUsersLoaded(List<UserVO> userList) {
        //Notify that the data is ready - using LocalBroadcast
        mUserList = userList;

        //keep the data in persistent layer.
        UserVO.saveUsers(mUserList);
    }

    public void notifyErrorInLoadingUsers(String message) {

    }

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

    public CourseVO getStoredFeaturedCourseData() {
        if (mFeaturedCourse == null) {
            mFeaturedCourse = CourseVO.loadFeaturedCourse();
        }

        return mFeaturedCourse;
    }

    public void setStoredData(List<CourseVO> courseList) {
        mCourseList = courseList;
    }

    //region CardData
    public void setLastAccessCardIndex(int cardIndex) {
        getStoredFeaturedCourseData().setLastAccessCardIndex(cardIndex);
    }

    public int getLastAccessCardIndex() {
        return getStoredFeaturedCourseData().getLastAccessCardIndex();
    }

    public void setCardListData(List<LessonCardVO> cardList) {
        mCurrentAccessCardList = cardList;
    }

    public List<LessonCardVO> getCardListData() {
        if (mCurrentAccessCardList == null || mCurrentAccessCardList.size() <= 0) {
            mCurrentAccessCardList = LessonCardVO.loadLessonCardsByCourseTitle(getStoredFeaturedCourseData().getTitle());
        }

        return mCurrentAccessCardList;
    }

    public LessonCardVO getLessonCardbyIndex(int cardIndex) {
        return getCardListData().get(cardIndex);
    }

    public int getFirstCardIndexbyChapterId(String chapterId) {
        for (LessonCardVO s : getCardListData()) {
            if (s.getChapterId().equals(chapterId)) {
                return mCurrentAccessCardList.indexOf(s);
            }
        }

        return 0;
    }

    public int getCardCountbyChapterId(String chapterId) {
        int count = 0;
        for (LessonCardVO s : getCardListData()) {
            if (s.getChapterId().equals(chapterId)) {
                count++;
            }
        }

        return count;
    }
    //endregion

    //region ChapterData
    public void setChapterListData(List<ChapterVO> chapterList) {
        mCurrentAccessChapterList = chapterList;
    }

    public List<ChapterVO> getChapterListData() {
        if (mCurrentAccessChapterList == null || mCurrentAccessChapterList.size() <= 0)
            mCurrentAccessChapterList = ChapterVO.loadChapterListByCourseTitle(getStoredFeaturedCourseData().getTitle());

        return mCurrentAccessChapterList;
    }

    public ChapterVO getChapterbyIndex(int chapterIndex) {
        return getChapterListData().get(chapterIndex);
    }

    //TODO check error
    public ChapterVO getChapterbyId(String chapterId) {
        for (ChapterVO s : getChapterListData()) {
            if (s.getChapterId().equals(chapterId)) {
                return s;
            }
        }

        return null;
    }

    public void setChapterFinishPercentage(String chapterId, int finishPercentage) {
        ChapterVO chapter = getChapterbyId(chapterId);
        if (chapter.getFinishedPercentage() < finishPercentage)
            chapter.setFinishedPercentage(finishPercentage);
    }

    public void setChapterUnLock(String chapterId) {
        ChapterVO chapter = getChapterbyId(chapterId);
        if (chapter.isLocked())
            chapter.setLocked(false);
    }
    //endregion

    //region Discussion
    public void setDiscussionListData(List<DiscussionVO> discussionList) {
        mCurrentAccessDiscussionList = discussionList;
    }

    public List<DiscussionVO> getDiscussionListData() {
        return mCurrentAccessDiscussionList;
    }

    public DiscussionVO getDiscussionbyId(String discussionId)
    {
        for (DiscussionVO s : mCurrentAccessDiscussionList) {
            if (s.getDiscussionId().equals(discussionId)) {
                return s;
            }
        }

        return null;
    }
    //endregion

    //region TodoList
    public void setTodoListData(List<TodoListVO> itemList) {
        mCurrentTodoList = itemList;
    }

    public List<TodoListVO> getTodoListData() {
        if (mCurrentTodoList == null || mCurrentTodoList.size() <= 0)
            mCurrentTodoList = TodoListVO.loadTodoListbyCourseTitle(getStoredFeaturedCourseData().getTitle());

        return mCurrentTodoList;
    }

    public TodoListVO getTodoListbyListId(String listId) {
        for (TodoListVO s : getTodoListData()) {
            if (s.getTodoListId().equals(listId)) {
                return s;
            }
        }

        return null;
    }

    public TodoListVO getTodoListbyCardId(String cardId) {
        for (TodoListVO s : getTodoListData()) {
            if (s.getCardId().equals(cardId)) {
                return s;
            }
        }

        return null;
    }
    //endregion

    //region TodoItemData
    public void setTodoItemListData(List<TodoItemVO> itemList) {
        mCurrentTodoItemList = itemList;
    }

    public List<TodoItemVO> getTodoItemListData() {
        return mCurrentTodoItemList;
    }
    //endregion
}
