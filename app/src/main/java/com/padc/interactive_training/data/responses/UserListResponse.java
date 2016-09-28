package com.padc.interactive_training.data.responses;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.data.vos.UserVO;

import java.util.ArrayList;

/**
 * Created by NayLinAung on 9/29/2016.
 */
public class UserListResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("users")
    private ArrayList<UserVO> userList;

    public int getCode() { return code; }

    public String getMessage() {
        return message;
    }

    public ArrayList<UserVO> getUserList() {
        return userList;
    }
}
