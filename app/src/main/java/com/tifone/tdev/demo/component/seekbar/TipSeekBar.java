package com.tifone.tdev.demo.component.seekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * Create by Tifone on 2019/4/14.
 */
public class TipSeekBar extends SeekBar {
    public TipSeekBar(Context context) {
        super(context);
    }

    public TipSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TipSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
