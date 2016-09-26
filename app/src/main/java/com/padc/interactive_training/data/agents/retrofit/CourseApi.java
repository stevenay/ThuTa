package com.padc.interactive_training.data.agents.retrofit;

import com.padc.interactive_training.data.responses.CourseListResponse;
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


}
