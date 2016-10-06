package com.padc.interactive_training;

import android.app.Application;
import android.content.Context;

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
