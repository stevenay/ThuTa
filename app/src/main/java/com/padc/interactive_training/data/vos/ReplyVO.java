package com.padc.interactive_training.data.vos;

import java.util.Date;

/**
 * Created by NayLinAung on 9/16/2016.
 */
public class ReplyVO {

    private int replyID;
    private String description;
    private String userName;
    private String postPastTime;
    private Date postedTime;

    public int getReplyID() {
        return replyID;
    }

    public void setReplyID(int replyID) {
        this.replyID = replyID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostPastTime() {
        return postPastTime;
    }

    public void setPostPastTime(String postPastTime) {
        this.postPastTime = postPastTime;
    }

    public Date getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(Date postedTime) {
        this.postedTime = postedTime;
    }
}
