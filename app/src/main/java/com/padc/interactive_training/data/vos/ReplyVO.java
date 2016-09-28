package com.padc.interactive_training.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.persistence.CoursesContract;

import java.util.Date;
import java.util.List;

/**
 * Created by NayLinAung on 9/16/2016.
 */
public class ReplyVO {

    @SerializedName("user_id")
    private String userId;

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
}
