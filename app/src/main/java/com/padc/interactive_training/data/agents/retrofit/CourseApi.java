package com.padc.interactive_training.data.agents.retrofit;

import com.padc.interactive_training.data.responses.ArticleListResponse;
import com.padc.interactive_training.data.responses.CourseListResponse;
import com.padc.interactive_training.data.responses.UserListResponse;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by NayLinAung on 9/22/16.
 */
public interface CourseApi {

    @FormUrlEncoded
    @POST(InteractiveTrainingConstants.API_GET_COURSE_LIST)
    Call<CourseListResponse> loadCourses(
            @Field(InteractiveTrainingConstants.PARAM_ACCESS_TOKEN) String param);

    @FormUrlEncoded
    @POST(InteractiveTrainingConstants.API_GET_USER_LIST)
    Call<UserListResponse> loadUsers(
            @Field(InteractiveTrainingConstants.PARAM_ACCESS_TOKEN) String param);

    @FormUrlEncoded
    @POST(InteractiveTrainingConstants.API_GET_ARTICLE_LIST)
    Call<ArticleListResponse> loadArticles(
            @Field(InteractiveTrainingConstants.PARAM_ACCESS_TOKEN) String param);
}

