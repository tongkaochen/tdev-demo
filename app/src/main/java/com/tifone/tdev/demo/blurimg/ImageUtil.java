package com.tifone.tdev.demo.blurimg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * Create by Tifone on 2019/4/23.
 */
public class ImageUtil {
    public static Bitmap translateDrawableToBitmap(Drawable drawable) {
        return translateDrawableToBitmap(drawable, 1);
    }
    public static Bitmap translateDrawableToBitmap(Drawable drawable, int sampling) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        if (width == -1 || height == -1) {
            return null;
        }
        // scale bitmap with sampling
        drawable.setBounds(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width / sampling,
                height / sampling, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1.0f / sampling, 1.0f / sampling);
        drawable.draw(canvas);
        return bitmap;
    }
}
