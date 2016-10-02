package com.padc.interactive_training.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.padc.interactive_training.InteractiveTrainingApp;

/**
 * Created by NayLinAung on 9/1/2016.
 */
/**
 * Created by NayLinAung on 9/1/2016.
 */
public class ScreenUtils {
    private static int screenWidth = 0;
    private static int screenHeight = 0;

    private static ScreenUtils objInstance;

    private Context context;

    private ScreenUtils(){
        context = InteractiveTrainingApp.getContext();
    }

    public static ScreenUtils getObjInstance(){
        if(objInstance == null){
            objInstance = new ScreenUtils();
        }
        return objInstance;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Get pixel from dpi.
     * @param dpi
     * @return
     */
    public int getPixelFromDPI(float dpi){
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, context.getResources().getDisplayMetrics()));
    }

    /**
     * Get absolute width of the screen.
     * @return
     */
    public int getWidthOfScreen() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        return metrics.widthPixels;
    }

    /**
     * Get absoute height of the screen.
     * @return
     */
    public int getHeightOfScreen() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;
    }

    /**
     * Show soft keyboard in some situation.
     */
    public void showSoftKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Service.INPUT_METHOD_SERVICE);
        if(imm != null){
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * Hide soft keyboard based on the EditText which the focus is in.
     * @param et
     */
    public void hideSoftKeyboard(EditText et){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Service.INPUT_METHOD_SERVICE);
        if(imm != null){
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusbarColor(int colorReference, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color
            window.setStatusBarColor(activity.getResources().getColor(colorReference));
        }
    }

    public static Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}

