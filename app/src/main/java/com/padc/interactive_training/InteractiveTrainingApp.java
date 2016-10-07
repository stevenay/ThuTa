package com.padc.interactive_training;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

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
