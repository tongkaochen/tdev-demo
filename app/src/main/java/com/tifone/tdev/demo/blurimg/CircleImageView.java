package com.tifone.tdev.demo.blurimg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Create by Tifone on 2019/4/23.
 */
public class CircleImageView extends ImageView {
    private Paint mPaint;
    private PorterDuffXfermode mMode;
    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mMode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            Bitmap bitmap = ImageUtil.changeDrawableToBitmap(getDrawable());
            bitmap = scaleBitmap(bitmap);
            bitmap = toRoundBitmap(bitmap);
            setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//
//        int count = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint);
//        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
//        mPaint.setXfermode(mMode);
        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, getWidth() / 2.0f, mPaint);
//        mPaint.setXfermode(null);
//        canvas.restoreToCount(count);
    }
    private Bitmap scaleBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // scale
        float widthRatio = (float) getWidth() / width;
        float heightRatio = (float) getHeight() / height;

        float maxRatio = Math.max(widthRatio, heightRatio);
        Matrix matrix = new Matrix();
        matrix.reset();
        int diffWidth = getWidth() - width;
        int diffHeight = getHeight() - height;
        matrix.postScale(maxRatio, maxRatio, width / 2.0f, height / 2.0f);
        matrix.postTranslate(diffWidth / 2.0f, diffHeight / 2.0f);
        Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return result;
    }

    public Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        //paint.setColor(color);

        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);

        return output;
    }
}
