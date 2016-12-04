package com.padc.interactive_training.activities;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.CourseOutlineAdapter;
import com.padc.interactive_training.utils.TransitionHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseOverviewActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.rv_course_outline)
    RecyclerView rvCourseOutline;

    private CourseOutlineAdapter courseOutlineAdapter;
    private static final String IE_COURSE_TITLE = "IE_COURSE_TITLE";

    public static Intent newIntent(String courseTitle) {
        Intent intent = new Intent(InteractiveTrainingApp.getContext(), CourseOverviewActivity.class);
        intent.putExtra(IE_COURSE_TITLE, courseTitle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_overview);
        ButterKnife.bind(this, this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        collapsingToolbar.setTitle(" ");

        ArrayList<String> outlineList = new ArrayList<>();
        outlineList.add("လူတိုင္း UX ဆိုတဲ့ သေဘာတရားနဲ႔ ရင္းႏွီးသင့္ပါတယ္.");
        outlineList.add("UX ဆိုတာ User ေတြအတြက္ အသံုးျပဳရပိုလြယ္သြားေအာင္ လုပ္ေပးနိုင္ပါတယ္.");
        outlineList.add("UX စဥ္းစားတဲ့အခါ Brand Identity နဲ႔ ဆက္စပ္ၿပီး ပံုေဖာ္သင့္ပါတယ္.");
        outlineList.add("ကိုယ္တည္ေဆာက္မယ့္ App ရဲ႕ Goals ေတြေပၚမွာ UX သည္ အမ်ားၿကီး မွီတည္ေနပါတယ္.");
        outlineList.add("UX ေကာင္းရင္ ကိုယ့္ App ကို လူေတြ ပိုႀကိဳက္နွစ္သက္ၾကပါတယ္.");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvCourseOutline.setLayoutManager(linearLayoutManager);

        courseOutlineAdapter = new CourseOutlineAdapter(outlineList);
        rvCourseOutline.setAdapter(courseOutlineAdapter);
    }

    private void bindData()
    {

    }

    @OnClick(R.id.btn_start_course)
    public void onClickStartCourse(View view)
    {
        Intent intent = RegisteredCourseDetailActivity.newIntent("");

        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivity(intent, transitionActivityOptions.toBundle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return true;
    }
}
