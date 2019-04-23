package com.tifone.tdev.demo.blurimg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;


// use to blur image
public class BlurTransaction {

    private final RenderScript mRenderScript;
    private final ScriptIntrinsicBlur mBlurRender;
    private Context mContext;

    public BlurTransaction(Context context) {
        mContext = context;
        mRenderScript = RenderScript.create(context);
        mBlurRender = ScriptIntrinsicBlur.create(mRenderScript, Element.U8_4(mRenderScript));
    }

    public Bitmap transaction(Bitmap target) {
        if (target.getWidth() <= 0 || target.getHeight() <= 0) {
            return null;
        }
        Bitmap blurredBitmap = Bitmap.createBitmap(target.getWidth(),
                target.getHeight(), target.getConfig());
        Allocation input = Allocation.createFromBitmap(mRenderScript, target,
                Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
        Allocation output = Allocation.createTyped(mRenderScript, input.getType());

        mBlurRender.setRadius(25);
        input.copyFrom(target);
        mBlurRender.setInput(input);
        mBlurRender.forEach(output);
        output.copyTo(blurredBitmap);
        return blurredBitmap;
    }

    public Bitmap transaction(int resourceId) {
        return transaction(mContext.getDrawable(resourceId));
    }

    public Bitmap transaction(Drawable drawable) {

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
        return transaction(bitmap);
    }

}
