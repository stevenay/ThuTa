package com.padc.interactive_training.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;

/**
 * Created by NayLinAung on 10/2/2016.
 */
public class ImageUtils {

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap drawableBitmap() {
        Context mContext = InteractiveTrainingApp.getContext();
        Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_face_24dp);
        return icon;
    }

    public static Bitmap addWhiteBorderToBitmap(Bitmap bmp, int borderSize) {
        Context mContext = InteractiveTrainingApp.getContext();
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 10, bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawColor(mContext.getResources().getColor(R.color.text_white_ish));

        final Paint paint = new Paint();
        Rect r = new Rect(0, 0, bmpWithBorder.getWidth(), bmpWithBorder.getHeight());
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2); // set stroke width
        paint.setColor(mContext.getResources().getColor(R.color.chapter_disabled_text_color)); // set stroke color
        canvas.drawRect(r, paint);

        canvas.drawBitmap(bmp, borderSize, 20, null);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thuta_icon, options);
        canvas.drawBitmap(bitmap, 25, bmp.getHeight()+25, null);
        return bmpWithBorder;
    }

}
