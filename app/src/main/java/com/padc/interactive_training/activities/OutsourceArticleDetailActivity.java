package com.padc.interactive_training.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.components.MMTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutsourceArticleDetailActivity extends AppCompatActivity {

    @BindView(R.id.wv_web)
    WebView wvWeb;

    @BindView(R.id.tv_screen_title)
    MMTextView tvScreenTitle;

    private final static String IE_OUTSOURCE_URL = "IE_OUTSOURCE_URL";
    private final static String IE_OUTSOURCE_Name = "IE_OUTSOURCE_Name";
    private String mOutsourceUrl = "";
    private String mOutsourceName = "";

    public static Intent newIntent(String outsourceUrl, String outsourceName) {
        Intent newIntent = new Intent(InteractiveTrainingApp.getContext(), OutsourceArticleDetailActivity.class);
        newIntent.putExtra(IE_OUTSOURCE_URL, outsourceUrl);
        newIntent.putExtra(IE_OUTSOURCE_Name, outsourceName);
        return newIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outsource_article_detail);
        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mOutsourceUrl = getIntent().getStringExtra(IE_OUTSOURCE_URL);
        mOutsourceName = getIntent().getStringExtra(IE_OUTSOURCE_Name);
        bindToWebView(mOutsourceUrl);
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

    private void bindToWebView(String mOutsourceUrl) {
        tvScreenTitle.setText(mOutsourceName);

        // Websettings to setup the webview
        WebSettings webSettings = wvWeb.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCachePath(this.getCacheDir().getAbsolutePath());
        webSettings.setAppCacheEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        wvWeb.setWebChromeClient(new WebChromeClient());

        wvWeb.requestFocus(View.FOCUS_DOWN);
        wvWeb.setFocusable(true);

        wvWeb.loadUrl(mOutsourceUrl);
    }


}
