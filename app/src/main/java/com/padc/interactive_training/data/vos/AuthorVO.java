package com.padc.interactive_training.data.vos;

import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.persistence.CoursesContract;

/**
 * Created by NayLinAung on 9/26/2016.
 */
public class AuthorVO {

    @SerializedName("author_name")
    private String authorName;

    @SerializedName("profile_photo_url")
    private String profilePhotoUrl;

    @SerializedName("email")
    private String email;

    @SerializedName("description")
    private String description;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static AuthorVO loadAuthorByName(String authorName) {
        Context context = InteractiveTrainingApp.getContext();
        AuthorVO author = new AuthorVO();

        Cursor cursor = context.getContentResolver().query(CoursesContract.AuthorEntry.buildAuthorUriWithAuthorName(authorName),
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            author.setAuthorName(cursor.getString(cursor.getColumnIndex(CoursesContract.AuthorEntry.COLUMN_AUTHOR_NAME)));
        }

        return author;
    }
}
