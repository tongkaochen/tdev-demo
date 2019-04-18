package com.tifone.tdev.demo.nestedview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class NestedChildView extends View implements NestedScrollingChild, ViewScrollingCallback {

    private NestedScrollingChildHelper mScrollingHelper;

    private int[] consumed = new int[2];
    private int[] offsetWindow = new int[2];
    private float mDownX;
    private float mDownY;
    private float mLastX;
    private float mLastY;

    public NestedChildView(Context context) {
        this(context, null);
    }

    public NestedChildView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScrollingHelper = new NestedScrollingChildHelper(this);
        mScrollingHelper.setNestedScrollingEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("tifone", "child onTouchEvent");
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                mLastX = x;
                mLastY = y;
                startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL
                        | ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (x - mDownX);
                int dy = (int) (y - mDownY);
                if (dispatchNestedPreScroll(dx, dy, consumed, offsetWindow)) {
                    dx -= consumed[0];
                    dy -= consumed[1];
                }
                offsetLeftAndRight(dx);
                offsetTopAndBottom(dy);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                stopNestedScroll();
                break;

        }
        mLastX = x;
        mLastY = y;
        return true;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mScrollingHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mScrollingHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mScrollingHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mScrollingHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mScrollingHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed,
                                        int dyConsumed,
                                        int dxUnconsumed,
                                        int dyUnconsumed,
                                        @Nullable int[] offsetInWindow) {
        return mScrollingHelper.dispatchNestedScroll(dxConsumed,
                dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy,
                                           @Nullable int[] consumed,
                                           @Nullable int[] offsetInWindow) {
        return mScrollingHelper.dispatchNestedPreScroll(
                dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX,
                                       float velocityY,
                                       boolean consumed) {
        return mScrollingHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mScrollingHelper.onDetachedFromWindow();
    }

    @Override
    public void onOffsetChanged(int dx, int dy) {
        ViewGroup parent = (ViewGroup) getParent();
        if (dx > 0) {
            if (getLeft() - dx > 0) {
                offsetLeftAndRight(-dx);
            }
        } else {
            if (getRight() - dx < parent.getWidth()) {
                offsetLeftAndRight(-dx);
            }
        }
        if (dy > 0) {
            if (getTop() - dy > 0) {
                offsetTopAndBottom(-dy);
            }
        } else {
            if (getBottom() - dy < parent.getHeight()) {
                offsetTopAndBottom(-dy);
            }
        }
    }
}
