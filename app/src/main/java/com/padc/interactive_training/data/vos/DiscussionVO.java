package com.padc.interactive_training.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.persistence.CoursesContract;

import java.util.Date;
import java.util.List;

/**
 * Created by NayLinAung on 9/16/2016.
 */
public class DiscussionVO {

    @SerializedName("discussion_id")
    private String discussionId;

    @SerializedName("discussion_title")
    private String discussionTitle;

    @SerializedName("description")
    private String description;

    @SerializedName("user_id")
    private String userId;

    private UserVO user;

    private String postPastTime;

    @SerializedName("post_datetime")
    private String postDateTime;

    @SerializedName("like_count")
    private Integer likes;

    @SerializedName("replies")
    private List<ReplyVO> replies;

    public String getTitle() {
        return discussionTitle;
    }

    public void setTitle(String title) {
        this.discussionTitle = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public String getPostPastTime() {
        return postPastTime;
    }

    public void setPostPastTime(String postPastTime) {
        this.postPastTime = postPastTime;
    }

    public String getPostDateTime() {
        return postDateTime;
    }

    public void setPostDateTime(String postedTime) {
        this.postDateTime = postedTime;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public List<ReplyVO> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyVO> replies) {
        this.replies = replies;
    }

    public String getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(String discussionId) {
        this.discussionId = discussionId;
    }

    public static void saveDiscussions(String courseTitle, List<DiscussionVO> discussions) {
        Log.d(InteractiveTrainingApp.TAG, "Method: saveDiscussion. Loaded discussions: " + discussions.size());

        ContentValues[] discussionCVs = new ContentValues[discussions.size()];
        for (int index = 0; index < discussions.size(); index++) {
            DiscussionVO discussion = discussions.get(index);
            discussionCVs[index] = discussion.parseToContentValues(courseTitle);

            ReplyVO.saveReplies(discussion.getDiscussionId(), discussion.getReplies());
            Log.d(InteractiveTrainingApp.TAG, "Method: saveDiscussions. Discussion Title: " + discussion.getTitle());
        }

        Context context = InteractiveTrainingApp.getContext();
        int insertCount = context.getContentResolver().bulkInsert(CoursesContract.DiscussionEntry.CONTENT_URI, discussionCVs);

        Log.d(InteractiveTrainingApp.TAG, "Bulk inserted into discussions table : " + insertCount);
    }

    public ContentValues parseToContentValues(String courseTitle) {

        ContentValues cv = new ContentValues();
        cv.put(CoursesContract.DiscussionEntry.COLUMN_COURSE_TITLE, courseTitle);
        cv.put(CoursesContract.DiscussionEntry.COLUMN_DISCUSSION_ID, discussionId);
        cv.put(CoursesContract.DiscussionEntry.COLUMN_DISCUSSION_TITLE, discussionTitle);
        cv.put(CoursesContract.DiscussionEntry.COLUMN_DESCRIPTION, description);
        cv.put(CoursesContract.DiscussionEntry.COLUMN_USER_ID, userId);
        cv.put(CoursesContract.DiscussionEntry.COLUMN_POST_DATETIME, postDateTime);
        cv.put(CoursesContract.DiscussionEntry.COLUMN_LIKE_COUNT, likes);

        return cv;
    }

    public static DiscussionVO parseFromCursor(Cursor data) {
        DiscussionVO discussion = new DiscussionVO();

        discussion.discussionId = data.getString(data.getColumnIndex(CoursesContract.DiscussionEntry.COLUMN_DISCUSSION_ID));
        discussion.discussionTitle = data.getString(data.getColumnIndex(CoursesContract.DiscussionEntry.COLUMN_DISCUSSION_TITLE));
        discussion.description = data.getString(data.getColumnIndex(CoursesContract.DiscussionEntry.COLUMN_DESCRIPTION));
        discussion.userId = data.getString(data.getColumnIndex(CoursesContract.DiscussionEntry.COLUMN_USER_ID));
        discussion.postDateTime = data.getString(data.getColumnIndex(CoursesContract.DiscussionEntry.COLUMN_POST_DATETIME));
        discussion.likes = data.getInt(data.getColumnIndex(CoursesContract.DiscussionEntry.COLUMN_LIKE_COUNT));

        return discussion;
    }
}
