package com.padc.interactive_training.data.vos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NayLinAung on 9/21/2016.
 */
public class CourseCategoryVO {

    @SerializedName("category_name")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
