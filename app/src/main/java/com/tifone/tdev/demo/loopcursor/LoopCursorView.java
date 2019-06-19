package com.tifone.tdev.demo.loopcursor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class LoopCursorView extends View {
    private Paint mLinePaint;
    public LoopCursorView(Context context) {
        this(context, null);
    }

    public LoopCursorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopCursorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.BLUE);
        mLinePaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.drawLine(getWidth() / 2, 0, getWidth()/ 2,getHeight()/2, mLinePaint);
    }
}
