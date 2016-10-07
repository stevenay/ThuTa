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
import com.padc.interactive_training.fragments.FeaturedCourseListFragment;
import com.padc.interactive_training.fragments.MyCourseListFragment;
import com.padc.interactive_training.utils.MMFontUtils;
import com.padc.interactive_training.utils.ScreenUtils;
import com.padc.interactive_training.utils.TransitionHelper;
import com.padc.interactive_training.views.holders.FeaturedCourseViewHolder;
import com.padc.interactive_training.views.holders.MyCourseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements MyCourseViewHolder.ControllerCourseItem,
        FeaturedCourseViewHolder.ControllerFeaturedCourseItem,
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.content)
    CoordinatorLayout clContent;

    @BindView(R.id.tv_screen_title)
    TextView tvScreenTitle;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

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

        Menu leftMenu = navigationView.getMenu();
        MMFontUtils.applyMMFontToMenu(leftMenu);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
            prepareIntroAnimation();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
            // showFeedLoadingItemDelayed();
        }
    }

    private void prepareIntroAnimation() {
        toolbar.setTranslationY(-ACTION_BAR_SIZE);
        tvScreenTitle.setTranslationY(-ACTION_BAR_SIZE);
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
        navigateToFeaturedCourseListFragment(true);
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
        switch (id) {
            case R.id.action_profile:
                // GAUtils.getInstance().sendAppAction(GAUtils.ACTION_TAP_SETTINGS);
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
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

    //region FeaturedCourseItemListener
    @Override
    public void onTapFeaturedCourse(CourseVO course) {
        Intent intent = RegisteredCourseDetailActivity.newIntent("SampleCourseName");

        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivity(intent, transitionActivityOptions.toBundle());
    }

    @Override
    public void onFeaturedCoverImageClick() {

    }
    //endregion

    //region Navigation menu and its related
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_featured_course:
                navigateToFeaturedCourseListFragment(false);
                return true;
            case R.id.nav_course_categories:
                Toast.makeText(getApplicationContext(), "You hit course categories.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_my_course:
                navigateToMyCourseListFragment();
                return true;
            case R.id.nav_notifications:
                return true;
            case R.id.nav_pin_cards:
                return true;
            case R.id.nav_articles:
                navigateToArticle();
                return true;
            case R.id.nav_local_training_center:
                return true;
        }

        return false;
    }

    //region Navigation
    private void navigateToMyCourseListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, MyCourseListFragment.newInstance())
                .commit();
    }

    private void navigateToFeaturedCourseListFragment(boolean animate) {
        if (animate)
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_up_fragment, R.anim.slide_up_out_fragment)
                    .replace(R.id.fl_container, FeaturedCourseListFragment.newInstance())
                    .commitAllowingStateLoss();
        else
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, FeaturedCourseListFragment.newInstance())
                    .commitAllowingStateLoss();
    }

    //endregion

    private void navigateToArticle() {
        Intent intent = ArticlesActivity.newIntent();
        startActivity(intent);
    }

    //endregion
}
