package com.padc.interactive_training.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.ReplyAdapter;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.persistence.CoursesContract;
import com.padc.interactive_training.data.vos.DiscussionVO;
import com.padc.interactive_training.data.vos.ReplyVO;
import com.padc.interactive_training.data.vos.UserVO;
import com.padc.interactive_training.utils.DateTimeUtils;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;
import com.padc.interactive_training.views.holders.ReplyViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscussionDetailActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_reply_list)
    RecyclerView rvReplyList;

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.tv_post_past_time)
    TextView tvPostPastTime;

    @BindView(R.id.tv_post_time)
    TextView tvPostTime;

    @BindView(R.id.tv_discussion_title)
    TextView tvDiscussionTitle;

    @BindView(R.id.tv_description)
    TextView tvDescription;

    @BindView(R.id.tv_likes_count)
    TextView tvLikesCount;

    @BindView(R.id.tv_reply_count)
    TextView tvReplyCount;

    private ReplyAdapter replyAdapter;
    private ReplyViewHolder.ControllerReplyItem controllerReplyItem;

    private static final String IE_DISCUSSION_ID = "IE_DISCUSSION_ID";
    private String mDiscussionId = "";
    private DiscussionVO mDiscussion;

    public static Intent newIntent(String discussionID) {
        Intent intent = new Intent(InteractiveTrainingApp.getContext(), DiscussionDetailActivity.class);
        intent.putExtra(IE_DISCUSSION_ID, discussionID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_detail);
        ButterKnife.bind(this, this);

        mDiscussionId = getIntent().getStringExtra(IE_DISCUSSION_ID);
        mDiscussion = CourseModel.getInstance().getDiscussionbyId(mDiscussionId);

        Drawable mDrawable = this.getResources().getDrawable(R.drawable.ic_face_24dp);
        mDrawable.setColorFilter(new
                PorterDuffColorFilter(0xFF9BBE74, PorterDuff.Mode.SRC_IN));
        this.ivProfile.setImageDrawable(mDrawable);

        bindReplyListData();
        bindDiscussionData(mDiscussion);

        getSupportLoaderManager().initLoader(InteractiveTrainingConstants.REPLY_LIST_LOADER, null, this);
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

    private void bindReplyListData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvReplyList.setLayoutManager(linearLayoutManager);

        replyAdapter = new ReplyAdapter(new ArrayList<ReplyVO>(), controllerReplyItem);
        rvReplyList.setAdapter(replyAdapter);
    }

    private void bindDiscussionData(DiscussionVO discussion) {
        Date today = new Date();
        Date postDate = DateTimeUtils.parseStringToDateTime(discussion.getPostDateTime());

        tvUserName.setText(discussion.getUser().getFullName());
        tvPostPastTime.setText(DateTimeUtils.formattedDateDifference(postDate, today));
        tvPostTime.setText(DateTimeUtils.parseDateToString("hh:mm a", postDate));
        tvDiscussionTitle.setText(discussion.getTitle());
        tvDescription.setText(discussion.getDescription());

        tvLikesCount.setText("Likes (" + String.valueOf(discussion.getLikes()) + ")");
        tvReplyCount.setText("Replies (" + String.valueOf(discussion.getReplies().size()) + ")");
    }

    //region
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                CoursesContract.ReplyEntry.buildReplyUriWithDiscussionId(mDiscussionId),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<ReplyVO> replyList = new ArrayList<>();

        if (data != null && data.moveToFirst()) {
            replyList = new ArrayList<>();

            do {
                ReplyVO reply = ReplyVO.parseFromCursor(data);
                reply.setUser(UserVO.loadUserbyUserId(reply.getUserId()));
                replyList.add(reply);
            } while (data.moveToNext());

            Log.d(InteractiveTrainingApp.TAG, "Retrieved replies DESC : " + replyList.size());
        }

        replyAdapter.setNewData(replyList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    //endregion
}
