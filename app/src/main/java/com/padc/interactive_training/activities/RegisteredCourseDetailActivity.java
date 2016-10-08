package com.padc.interactive_training.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.CourseHeaderPagerAdapter;
import com.padc.interactive_training.adapters.CoursePagerAdapter;
import com.padc.interactive_training.components.PageIndicatorView;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.persistence.CoursesContract;
import com.padc.interactive_training.data.vos.ChapterVO;
import com.padc.interactive_training.data.vos.CourseVO;
import com.padc.interactive_training.data.vos.DiscussionVO;
import com.padc.interactive_training.fragments.ChapterListFragment;
import com.padc.interactive_training.fragments.CourseInfoHeaderFragment;
import com.padc.interactive_training.fragments.CourseProgressHeaderFragment;
import com.padc.interactive_training.fragments.CourseTodoListFragment;
import com.padc.interactive_training.fragments.DiscussionListFragment;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;
import com.padc.interactive_training.utils.MMFontUtils;
import com.padc.interactive_training.utils.TransitionHelper;
import com.padc.interactive_training.views.holders.ChapterViewHolder;
import com.padc.interactive_training.views.holders.DiscussionViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisteredCourseDetailActivity extends AppCompatActivity
        implements ChapterViewHolder.ControllerChapterItem,
        DiscussionViewHolder.ControllerDiscussionItem,
        LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.appbar)
    AppBarLayout appBar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.tl_navigations)
    SmartTabLayout tlNavigations;

    @BindView(R.id.pager_navigations)
    ViewPager pagerNavigations;

    @BindView(R.id.pager_course_header)
    ViewPager pageCourseHeader;

    @BindView(R.id.fab_play_course)
    FloatingActionButton fabPlayCourse;

    @BindView(R.id.fab_add_discussion)
    FloatingActionButton fabAddDiscussion;

    @BindView(R.id.pi_course_header_pager)
    PageIndicatorView piCourseHeaderPager;

    protected static final int RC_COURSE_FLOW = 1236;
    private static final String IE_COURSE_TITLE = "IE_COURSE_TITLE";

    private CoursePagerAdapter mCoursePagerAdapter;
    private String mCourseTitle;
    private CourseVO mCourse;
    private ChapterListFragment chapterListFragment;

    public static Intent newIntent(String courseTitle) {
        Intent intent = new Intent(InteractiveTrainingApp.getContext(), RegisteredCourseDetailActivity.class);
        intent.putExtra(IE_COURSE_TITLE, courseTitle);
        return intent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_COURSE_FLOW) {
            if (CourseModel.getInstance().getChapterListData() != null && chapterListFragment != null && chapterListFragment.chapterAdapter != null)
                chapterListFragment.chapterAdapter.setNewData(CourseModel.getInstance().getChapterListData());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_course_detail);
        setupWindowAnimations();

        ButterKnife.bind(this, this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mCourseTitle = getIntent().getStringExtra(IE_COURSE_TITLE);
        mCoursePagerAdapter = new CoursePagerAdapter(getSupportFragmentManager());

        chapterListFragment = ChapterListFragment.newInstance(mCourseTitle);
        mCoursePagerAdapter.addTab(chapterListFragment, "CHAPTERS");
        mCoursePagerAdapter.addTab(DiscussionListFragment.newInstance(mCourseTitle), "DISCUSSION");
        mCoursePagerAdapter.addTab(CourseTodoListFragment.newInstance(mCourseTitle), "TODO-List (1)");

        pagerNavigations.setAdapter(mCoursePagerAdapter);
        pagerNavigations.setOffscreenPageLimit(mCoursePagerAdapter.getCount());

        tlNavigations.setViewPager(pagerNavigations);

        pagerNavigations.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (mCoursePagerAdapter.getPageTitle(position).toString().toLowerCase())
                {
                    case "discussion":
                        fabAddDiscussion.setVisibility(View.VISIBLE);
                        fabPlayCourse.setVisibility(View.INVISIBLE);
                        break;
                    case "chapters":
                        fabAddDiscussion.setVisibility(View.INVISIBLE);
                        fabPlayCourse.setVisibility(View.VISIBLE);
                        break;
                    default:
                        fabAddDiscussion.setVisibility(View.INVISIBLE);
                        fabPlayCourse.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        collapsingToolbar.setTitle(" ");
        getSupportLoaderManager().initLoader(InteractiveTrainingConstants.COURSE_DETAIL_LOADER, null, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void setupWindowAnimations() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(800);
            getWindow().setEnterTransition(explode);

            Fade slide = new Fade();
            slide.setDuration(500);
            getWindow().setReturnTransition(slide);
        }
    }

    private void bindData(final CourseVO courseVO) {
        MMFontUtils.applyMMFontToCollapsingToolbar(collapsingToolbar);

        // hide CollapsingToolbar Title on Expanded Condition
        // show only when Collapsed State
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(courseVO.getTitle());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        piCourseHeaderPager.setNumPage(2);

        CourseHeaderPagerAdapter pagerAdapter = new CourseHeaderPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addTab(CourseInfoHeaderFragment.newInstance(mCourse.getTitle(), mCourse.getCoverPhotoUrl()), "CourseInfo");
        pagerAdapter.addTab(CourseProgressHeaderFragment.newInstance(), "CourseProgress");

        pageCourseHeader.setAdapter(pagerAdapter);
        pageCourseHeader.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                piCourseHeaderPager.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //region LoaderPattern
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                CoursesContract.CourseEntry.buildCourseUriWithTitle(mCourseTitle),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            mCourse = CourseVO.parseFromCursor(data);
            bindData(mCourse);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    //endregion

    //region ClickEvents
    @OnClick(R.id.fab_play_course)
    public void onClickFabPlayCourse(View view) {
        this.navigateToCourseFlow(mCourseTitle, "", CourseModel.getInstance().getLastAccessCardIndex());
    }

    @OnClick(R.id.fab_add_discussion)
    public void onClickFabAddDiscussion(View view) {
        navigateToNewDiscussion(1); // need to pass Course ID
    }
    //endregion

    //region NavigationMethods
    private void navigateToCourseFlow(String courseTitle, String chapterId, int lastAccessCard) {
        Intent intent = CourseFlowActivity.newIntent(courseTitle, chapterId, lastAccessCard);

        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivityForResult(intent, RC_COURSE_FLOW);
    }

    private void navigateToNewDiscussion(Integer courseID)
    {
        Intent intent = NewDiscussionActivity.newIntent(courseID);
        startActivity(intent);
    }

    private void navigateToDiscussionDetail(String discussionId) {
        Intent intent = DiscussionDetailActivity.newIntent(discussionId);
        startActivity(intent);
    }
    //endregion

    //region ChapterController
    @Override
    public void onTapChapter(ChapterVO chapter) {
        if (!chapter.isLocked())
            navigateToCourseFlow(mCourse.getTitle(), chapter.getChapterId(), -1);
        else {
            Toast.makeText(getApplicationContext(), "ေရွ႕မွ အခန္းမ်ားကို အရင္ၿပီးေအာင္ ဖတ္ေပးပါ။", Toast.LENGTH_SHORT).show();
        }

    }
    //endregion

    //region DiscussionController
    @Override
    public void onTapDiscussion(DiscussionVO discussion) {
        navigateToDiscussionDetail(discussion.getDiscussionId());
    }

    @Override
    public void onTapLikeButton(Integer discussionID) {

    }
    //endregion
}

