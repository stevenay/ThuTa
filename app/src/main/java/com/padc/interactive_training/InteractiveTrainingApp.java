package com.padc.interactive_training;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Dell on 9/4/2016.
 */
public class InteractiveTrainingApp extends Application {

    public static final String TAG = "InteractiveTrainingApp";
    private static String articleTextSize = "medium";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        context = getApplicationContext();

        SharedPreferences prefs = getSharedPreferences(getString(R.string.ArticlePreference), MODE_PRIVATE);
        articleTextSize = prefs.getString(getString(R.string.ArticleTextSizePara), "medium");
    }

    public static Context getContext() {
        return context;
    }

    public static String getArticleTextSize() {
        return articleTextSize;
    }

    public static void setArticleTextSize(String articleTextSize) {
        InteractiveTrainingApp.articleTextSize = articleTextSize;
    }
}
