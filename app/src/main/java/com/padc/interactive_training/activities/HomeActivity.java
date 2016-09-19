package com.padc.interactive_training.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.MyCourseAdapter;
import com.padc.interactive_training.animators.RecyclerItemAnimator;
import com.padc.interactive_training.data.vos.CourseVO;
import com.padc.interactive_training.utils.ScreenUtils;
import com.padc.interactive_training.utils.TransitionHelper;
import com.padc.interactive_training.views.holders.MyCourseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements MyCourseViewHolder.ControllerCourseItem, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.content)
    CoordinatorLayout clContent;

    @BindView(R.id.rvMyCourse)
    RecyclerView rvMyCourse;

    @BindView(R.id.fab_search)
    FloatingActionButton fabSearch;

    @BindView(R.id.tv_screen_title)
    TextView tvScreenTitle;

    private MenuItem inboxMenuItem;

    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";
    public static final int ACTION_BAR_SIZE = ScreenUtils.dpToPx(56);

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

    private MyCourseAdapter myCourseAdapter;
    private boolean pendingIntroAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this, this);
        setupWindowAnimations();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupMyCourse();

        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
            prepareIntroAnimation();
        } else {
            myCourseAdapter.updateItems(false, new ArrayList<CourseVO>());
        }
    }

    private void setupMyCourse() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvMyCourse.setLayoutManager(linearLayoutManager);

        myCourseAdapter = new MyCourseAdapter(new ArrayList<CourseVO>(), this);
        rvMyCourse.setAdapter(myCourseAdapter);

        rvMyCourse.setItemAnimator(new RecyclerItemAnimator());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
            showFeedLoadingItemDelayed();
        }
    }

    private void prepareIntroAnimation() {
        fabSearch.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
        toolbar.setTranslationY(-ACTION_BAR_SIZE);
        tvScreenTitle.setTranslationY(-ACTION_BAR_SIZE);
    }

    private void showFeedLoadingItemDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvMyCourse.smoothScrollToPosition(0);
                myCourseAdapter.showLoadingView();
            }
        }, 500);
    }

    private void startIntroAnimation() {

        inboxMenuItem.getActionView().setTranslationY(-ACTION_BAR_SIZE);

        toolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        tvScreenTitle.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        inboxMenuItem.getActionView().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startContentAnimation();
                    }
                })
                .start();
    }

    private void startContentAnimation() {
        fabSearch.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();

        myCourseAdapter.updateItems(true, prepareSampleCourseList());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);

        inboxMenuItem = menu.findItem(R.id.action_profile);
        inboxMenuItem.setActionView(R.layout.menu_item_view);

        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade explode = new Fade();
            explode.setDuration(800);
            getWindow().setExitTransition(explode);
        }
    }

    public List<CourseVO> prepareSampleCourseList() {
        List<CourseVO> courseList = new ArrayList<>();

        CourseVO courseOne = new CourseVO();
        courseOne.setTitle("UV ေရာင္ျခည္ကို ဘယ္လိုကာကြယ္မလဲ");
        courseOne.setCategoryName("LifeStyle");
        courseOne.setDurationInMinute(15);
        courseOne.setAuthorName("Admin Team");
        courseOne.setColorCode("#aed582");
        courseOne.setImageUrl("co_terrace.png");
        courseList.add(courseOne);

        CourseVO courseTwo = new CourseVO();
        courseTwo.setTitle("အားကစားကို နည္းမွန္လမ္းမွန္ ျပဳလုပ္နည္းမ်ား");
        courseTwo.setCategoryName("Sports and Fitness");
        courseTwo.setDurationInMinute(15);
        courseTwo.setAuthorName("Admin Team");
        courseTwo.setColorCode("#81c683");
        courseOne.setImageUrl("co_runner.png");
        courseList.add(courseTwo);

        CourseVO courseThree = new CourseVO();
        courseThree.setTitle("C# အသံုးျပဳ Console Application တစ္ခု ဘယ္လိုတည္ေဆာက္မလဲ");
        courseThree.setCategoryName("Programming");
        courseThree.setDurationInMinute(10);
        courseThree.setAuthorName("Admin Team");
        courseThree.setColorCode("#25c6da");
        courseOne.setImageUrl("co_terrace.png");
        courseList.add(courseThree);

        return courseList;
    }

    //region ControllerCourseItem Implementation
    @Override
    public void onTapCourse(CourseVO course) {
        Intent intent = RegisteredCourseDetailActivity.newIntent("SampleCourseName");

        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivity(intent, transitionActivityOptions.toBundle());
    }

    @Override
    public void onCommentsClick(View v, int position) {

    }

    @Override
    public void onMoreClick(View v, int position) {

    }

    @Override
    public void onProfileClick(View v) {

    }

    @Override
    public void onCoverImageClick() {

    }


    //endregion

    //region Navigation menu and its related
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_my_course) {
            Toast.makeText(getApplicationContext(), "You hit my course option", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_my_test) {
            Toast.makeText(getApplicationContext(), "You hit my test option", Toast.LENGTH_SHORT).show();
            //navigateToMyTest();
        }
        else if (id == R.id.nav_explore) {
            Toast.makeText(getApplicationContext(), "You hit explore option", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_notifications) {
            Toast.makeText(getApplicationContext(), "You hit notification option", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_pin_cards) {
            Toast.makeText(getApplicationContext(), "You hit pin cards option", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_articles) {
            Toast.makeText(getApplicationContext(), "You hit articles option", Toast.LENGTH_SHORT).show();
            navigateToArticle();
        }
        else if (id == R.id.nav_local_training_center) {
            Toast.makeText(getApplicationContext(), "You hit local training center option", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    /*private void navigateToMyTest() {
        Intent intent = MyTestActivity.newIntent();
        startActivity(intent);
    }*/

    private void navigateToArticle() {
        Intent intent = ArticlesActivity.newIntent();
        startActivity(intent);
    }

    //endregion
}
