package com.padc.interactive_training.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewDiscussionActivity extends AppCompatActivity {

    @BindView(R.id.spinner_topic)
    Spinner spinnerTopic;

    private String[] arraySpinner;

    private static final String IE_DISCUSSION_ID = "IE_DISCUSSION_ID";

    public static Intent newIntent(Integer discussionID) {
        Intent intent = new Intent(InteractiveTrainingApp.getContext(), NewDiscussionActivity.class);
        intent.putExtra(IE_DISCUSSION_ID, discussionID);
        return intent;
    }

    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(this, RegisteredCourseDetailActivity.class);

        bundle.putString(InteractiveTrainingConstants.SWITCH_TAB, InteractiveTrainingConstants.TAB_DISCUSSION); // Both constants are defined in your code
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discussion);
        ButterKnife.bind(this, this);

        this.arraySpinner = new String[] {
            "General Topic",
            "1. Wear broad-spectrum sunscreen",
            "2. Put on protective clothing",
            "3. Use UV-blocking sunglasses",
            "4. Limiting Your Overall Exposure to UV Rays.",
            "5. Stay out of the sun during peak UV rediation times"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                                        R.layout.view_item_discussion_topic, arraySpinner);
        spinnerTopic.setAdapter(adapter);
    }
}
