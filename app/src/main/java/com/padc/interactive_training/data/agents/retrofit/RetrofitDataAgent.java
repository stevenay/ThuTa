package com.padc.interactive_training.data.agents.retrofit;

import android.util.Log;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.agents.CourseDataAgent;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.responses.CourseListResponse;
import com.padc.interactive_training.utils.CommonInstances;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NayLinAung on 9/26/16.
 */
public class RetrofitDataAgent implements CourseDataAgent {

    private static RetrofitDataAgent objInstance;

    private final CourseApi theApi;

    private RetrofitDataAgent() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(InteractiveTrainingConstants.COURSE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(CommonInstances.getGsonInstance()))
                .client(okHttpClient)
                .build();

        theApi = retrofit.create(CourseApi.class);
    }

    public static RetrofitDataAgent getInstance() {
        if (objInstance == null) {
            objInstance = new RetrofitDataAgent();
        }
        return objInstance;
    }

    @Override
    public void loadCourses() {
        Call<CourseListResponse> loadCourseCall = theApi.loadCourses(InteractiveTrainingConstants.ACCESS_TOKEN);
        loadCourseCall.enqueue(new Callback<CourseListResponse>() {
            @Override
            public void onResponse(Call<CourseListResponse> call, Response<CourseListResponse> response) {
                Log.d(InteractiveTrainingApp.TAG, "OnResponse");
                CourseListResponse courseListResponse = response.body();
                if (courseListResponse == null) {
                    CourseModel.getInstance().notifyErrorInLoadingCourses(response.message());
                } else {
                    CourseModel.getInstance().notifyCoursesLoaded(courseListResponse.getCourseList());
                }
            }

            @Override
            public void onFailure(Call<CourseListResponse> call, Throwable throwable) {
                Log.d(InteractiveTrainingApp.TAG, "OnFailure " + throwable.getMessage());
                CourseModel.getInstance().notifyErrorInLoadingCourses(throwable.getMessage());
            }
        });
    }

}
