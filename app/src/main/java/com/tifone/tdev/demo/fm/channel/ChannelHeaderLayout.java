package com.tifone.tdev.demo.fm.channel;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tifone.tdev.demo.R;

public class ChannelHeaderLayout extends RelativeLayout {
    private float lastY;
    private float totalDistance;
    private TextView mAllChannelTv;
    private TextView mFavouriteChannelTv;
    private TextView mScanButton;
    private boolean needInterceptByParent;

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
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAllChannelTv = findViewById(R.id.all_channel);
        mFavouriteChannelTv = findViewById(R.id.favourite_channel);
        mScanButton = findViewById(R.id.scan_btn);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("tifone", "intercept event " + ev.getAction());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                needInterceptByParent = true;
                return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP
                || ev.getAction() == MotionEvent.ACTION_CANCEL
                || ev.getAction() == MotionEvent.ACTION_DOWN) {
            needInterceptByParent = false;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("tifone", "Header touch " + event.getAction());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                return false;
        }
        return super.onTouchEvent(event);
    }

    public boolean needInterceptByParent() {
        return needInterceptByParent;
    }
}
