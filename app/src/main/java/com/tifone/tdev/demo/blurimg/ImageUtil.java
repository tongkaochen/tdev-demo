package com.tifone.tdev.demo.blurimg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * Create by Tifone on 2019/4/23.
 */
public class ImageUtil {
    public static Bitmap changeDrawableToBitmap(Drawable drawable) {

        int sampling = 6;
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        if (width == -1 || height == -1) {
            return null;
        }
        // scale bitmap to smaller
        drawable.setBounds(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width / sampling,
                height / sampling, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1.0f / sampling, 1.0f / sampling);
        drawable.draw(canvas);
        return bitmap;
    }
}
