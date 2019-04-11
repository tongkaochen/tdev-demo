package com.tifone.tdev.demo.fm.channel;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class ChannelHeaderLayout extends RelativeLayout {
    private float lastY;
    private float totalDistance;
    private DragHeaderCallback mCallback;
    public ChannelHeaderLayout(Context context) {
        super(context);
    }

    public ChannelHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChannelHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("tifone", "intercept event " + ev.getAction());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getRawY() - lastY) > getHeight() / 3) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.d("tifone", "touchevent " + event.getAction());
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float currentY = event.getRawY();
            Log.d("tifone", "drag.... lastY = " + lastY + " currenY = " + currentY);
            float diff = currentY - lastY;
            totalDistance += diff;
            if (diff > 0) {
                Log.d("tifone", "drag down, " + diff + " total :" + totalDistance);
            } else {
                Log.d("tifone", "drag up " + diff +  " total :" + totalDistance);
            }
            if (mCallback != null) {
                mCallback.onHeaderDragged(-diff);
            }
            lastY = currentY;
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mCallback != null) {
                mCallback.onDragRelease();
            }
        }
        return super.onTouchEvent(event);
    }
    public void setDragHeaderCallback(DragHeaderCallback callback) {
        mCallback = callback;
    }
    interface DragHeaderCallback {
        void onHeaderDragged(float diff);
        void onDragRelease();
    }
}
