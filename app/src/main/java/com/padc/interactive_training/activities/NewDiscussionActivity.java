package com.padc.interactive_training.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discussion);
        ButterKnife.bind(this, this);

        this.arraySpinner = new String[] {
            "General Topic",
            "1. Everyone should familiar with UX",
            "2. Making complex processes easy",
            "3. Be true to brand identity",
            "4. Depends on your app's goals",
            "5. Good UX encourge people"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                                        R.layout.view_item_discussion_topic, arraySpinner);
        spinnerTopic.setAdapter(adapter);
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
}
