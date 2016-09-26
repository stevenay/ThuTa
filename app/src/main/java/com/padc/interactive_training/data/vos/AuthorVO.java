package com.padc.interactive_training.data.vos;

import com.google.gson.annotations.SerializedName;

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
}
