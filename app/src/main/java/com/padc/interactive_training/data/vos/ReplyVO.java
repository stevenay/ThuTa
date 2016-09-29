package com.padc.interactive_training.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.persistence.CoursesContract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by NayLinAung on 9/16/2016.
 */
public class ReplyVO {

    private String discussionId;

    @SerializedName("user_id")
    private String userId;

    private UserVO user;

    @SerializedName("reply")
    private String reply;

    @SerializedName("reply_datetime")
    private String replyDateTime;

    @SerializedName("like_count")
    private int likeCount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReplyDateTime() {
        return replyDateTime;
    }

    public void setReplyDateTime(String replyDateTime) {
        this.replyDateTime = replyDateTime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(String discussionId) {
        this.discussionId = discussionId;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public static void saveReplies(String discussionId, List<ReplyVO> replies) {
        Log.d(InteractiveTrainingApp.TAG, "Method: reply. Loaded replies: " + replies.size());

        ContentValues[] replyCVs = new ContentValues[replies.size()];
        for (int index = 0; index < replies.size(); index++) {
            ReplyVO reply = replies.get(index);
            replyCVs[index] = reply.parseToContentValues(discussionId);

            Log.d(InteractiveTrainingApp.TAG, "Method: saveReplies. Reply Title: " + reply.getReply());
        }

        Context context = InteractiveTrainingApp.getContext();
        int insertCount = context.getContentResolver().bulkInsert(CoursesContract.ReplyEntry.CONTENT_URI, replyCVs);

        Log.d(InteractiveTrainingApp.TAG, "Bulk inserted into replies table : " + insertCount);
    }

    public ContentValues parseToContentValues(String discussionId) {
        ContentValues cv = new ContentValues();

        cv.put(CoursesContract.ReplyEntry.COLUMN_DISCUSSION_ID, discussionId);
        cv.put(CoursesContract.ReplyEntry.COLUMN_USER_ID, userId);
        cv.put(CoursesContract.ReplyEntry.COLUMN_REPLY, reply);
        cv.put(CoursesContract.ReplyEntry.COLUMN_REPLY_DATETIME, replyDateTime);
        cv.put(CoursesContract.ReplyEntry.COLUMN_LIKE_COUNT, likeCount);

        return cv;
    }

    public static List<ReplyVO> loadRepliesbyDiscussionId(String discussionId) {
        Context context = InteractiveTrainingApp.getContext();
        List<ReplyVO> replies = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(CoursesContract.ReplyEntry.buildReplyUriWithDiscussionId(discussionId),
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ReplyVO reply = new ReplyVO();
                reply.setDiscussionId(cursor.getString(cursor.getColumnIndex(CoursesContract.ReplyEntry.COLUMN_DISCUSSION_ID)));
                reply.setUserId(cursor.getString(cursor.getColumnIndex(CoursesContract.ReplyEntry.COLUMN_USER_ID)));
                reply.setReply(cursor.getString(cursor.getColumnIndex(CoursesContract.ReplyEntry.COLUMN_REPLY)));
                reply.setLikeCount(cursor.getInt(cursor.getColumnIndex(CoursesContract.ReplyEntry.COLUMN_LIKE_COUNT)));
                reply.setReplyDateTime(cursor.getString(cursor.getColumnIndex(CoursesContract.ReplyEntry.COLUMN_REPLY_DATETIME)));

                Log.d(InteractiveTrainingApp.TAG, "Load Replies by DiscussionId " + reply.getReply());

                replies.add(reply);
            } while (cursor.moveToNext());
        }

        return replies;
    }

    public static ReplyVO parseFromCursor(Cursor data) {
        ReplyVO reply = new ReplyVO();

        reply.userId = data.getString(data.getColumnIndex(CoursesContract.ReplyEntry.COLUMN_USER_ID));
        reply.reply = data.getString(data.getColumnIndex(CoursesContract.ReplyEntry.COLUMN_REPLY));
        reply.discussionId = data.getString(data.getColumnIndex(CoursesContract.ReplyEntry.COLUMN_DISCUSSION_ID));
        reply.replyDateTime = data.getString(data.getColumnIndex(CoursesContract.ReplyEntry.COLUMN_REPLY_DATETIME));
        reply.likeCount = data.getInt(data.getColumnIndex(CoursesContract.ReplyEntry.COLUMN_LIKE_COUNT));

        return reply;
    }

}
