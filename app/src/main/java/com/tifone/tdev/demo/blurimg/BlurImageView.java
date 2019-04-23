package com.tifone.tdev.demo.blurimg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class BlurImageView extends ImageView {
    private Drawable mSrcDrawable;
    private Bitmap mSrcBitmap;
    private RenderScript mRenderScript;
    private ScriptIntrinsicBlur mBlurRender;
    private boolean isBlurred;
    private boolean isInternalCalling;
    private BlurTransaction mBlurTransation;

    public BlurImageView(Context context) {
        this(context, null);
    }

    public BlurImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBlurTransation = new BlurTransaction(getContext());
        Log.d("tifone", "init begin");
        mRenderScript = RenderScript.create(getContext());
        mBlurRender = ScriptIntrinsicBlur.create(mRenderScript, Element.U8_4(mRenderScript));
        Log.d("tifone", "init end");
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        if (isInternalCalling) {
            super.setImageDrawable(drawable);
            isInternalCalling = false;
            return;
        }
        blurImage(drawable);
        Log.d("tifone", "setImageDrawable");
    }
    private void blurImage(Drawable drawable) {
        if (mBlurTransation == null) {
            mBlurTransation = new BlurTransaction(getContext());
        }
        isInternalCalling = true;
        Bitmap target = mBlurTransation.transaction(drawable);
        if (target == null) {
            isInternalCalling = false;
            return;
        }
        mSrcBitmap = target;
        setImageBitmap(target);
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
    }

    private void updateImageMatrix() {
        if (mSrcBitmap == null) {
            return;
        }
        int width = mSrcBitmap.getWidth();
        int height = mSrcBitmap.getHeight();

        // scale
        float widthRatio = (float) getWidth() / width;
        float heightRatio = (float) getHeight() / height;

        float maxRatio = Math.max(widthRatio, heightRatio);
        Matrix matrix = getImageMatrix();
        matrix.reset();
        int diffWidth = getWidth() - width;
        int diffHeight = getHeight() - height;
        matrix.postScale(maxRatio, maxRatio, width / 2.0f, height / 2.0f);
        matrix.postTranslate(diffWidth / 2.0f, diffHeight / 2.0f);
        setImageMatrix(matrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        updateImageMatrix();
        super.onDraw(canvas);
    }
}
