package com.padc.interactive_training.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.ReplyAdapter;
import com.padc.interactive_training.data.vos.ReplyVO;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;
import com.padc.interactive_training.views.holders.ReplyViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscussionDetailActivity extends AppCompatActivity {

    @BindView(R.id.rv_reply_list)
    RecyclerView rvReplyList;

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    private ReplyAdapter replyAdapter;
    private ReplyViewHolder.ControllerReplyItem controllerReplyItem;

    private static final String IE_DISCUSSION_ID = "IE_DISCUSSION_ID";

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

        if (savedInstanceState == null) {
            bindReplyListData();
        }

        Drawable mDrawable = this.getResources().getDrawable(R.drawable.ic_face_24dp);
        mDrawable.setColorFilter(new
                PorterDuffColorFilter(0xFF9BBE74, PorterDuff.Mode.SRC_IN));
        this.ivProfile.setImageDrawable(mDrawable);
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
}
