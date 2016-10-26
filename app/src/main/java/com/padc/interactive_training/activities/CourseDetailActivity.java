package com.padc.interactive_training.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;

public class CourseDetailActivity extends AppCompatActivity {

    private static final String IE_COURSE_TITLE = "IE_COURSE_TITLE";

    public static Intent newIntent(String courseTitle) {
        Intent intent = new Intent(InteractiveTrainingApp.getContext(), CourseDetailActivity.class);
        intent.putExtra(IE_COURSE_TITLE, courseTitle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
    }
}
