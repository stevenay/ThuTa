package com.padc.interactive_training.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.persistence.CoursesContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NayLinAung on 9/29/2016.
 */
public class UserVO {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("fullname")
    private String fullName;

    @SerializedName("profile_photo_url")
    private String profilePhotoUrl;

    @SerializedName("email")
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static void saveUsers(List<UserVO> users) {
        Log.d(InteractiveTrainingApp.TAG, "Method: user. Loaded users: " + users.size());

        ContentValues[] userCVs = new ContentValues[users.size()];
        for (int index = 0; index < users.size(); index++) {
            UserVO user = users.get(index);
            userCVs[index] = user.parseToContentValues();

            Log.d(InteractiveTrainingApp.TAG, "Method: saveUsers. User Name: " + user.getFullName());
        }

        Context context = InteractiveTrainingApp.getContext();
        int insertCount = context.getContentResolver().bulkInsert(CoursesContract.UserEntry.CONTENT_URI, userCVs);

        Log.d(InteractiveTrainingApp.TAG, "Bulk inserted into users table : " + insertCount);
    }

    public ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(CoursesContract.UserEntry.COLUMN_USER_ID, userId);
        cv.put(CoursesContract.UserEntry.COLUMN_NAME, fullName);
        cv.put(CoursesContract.UserEntry.COLUMN_EMAIL, email);
        cv.put(CoursesContract.UserEntry.COLUMN_PROFILE_PICTURE_URL, profilePhotoUrl);

        return cv;
    }

    public static UserVO loadUserbyUserId(String userId) {
        Context context = InteractiveTrainingApp.getContext();
        UserVO user = new UserVO();
        Cursor cursor = context.getContentResolver().query(CoursesContract.UserEntry.buildUserUriWithUserId(userId),
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            user.setUserId(cursor.getString(cursor.getColumnIndex(CoursesContract.UserEntry.COLUMN_USER_ID)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(CoursesContract.UserEntry.COLUMN_EMAIL)));
            user.setFullName(cursor.getString(cursor.getColumnIndex(CoursesContract.UserEntry.COLUMN_NAME)));
            user.setProfilePhotoUrl(cursor.getString(cursor.getColumnIndex(CoursesContract.UserEntry.COLUMN_PROFILE_PICTURE_URL)));

            Log.d(InteractiveTrainingApp.TAG, "Load Users by UserId " + user.getFullName());
        }

        return user;
    }

}
