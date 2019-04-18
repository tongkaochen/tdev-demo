package com.tifone.tdev.demo.nestedview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class NestedParentLayout extends FrameLayout implements NestedScrollingParent {

    private NestedScrollingParentHelper mScrollingHelper;
    private float mDownX;
    private float mDownY;
    private float mLastX;
    private float mLastY;
    private int[] consumed = new int[2];
    private int[] offsetWindow = new int[2];
    private ViewScrollingCallback mChildViewCallback;
    private View mChildView;

    public NestedParentLayout(@NonNull Context context) {
        this(context, null);
    }

    public NestedParentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedParentLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        mScrollingHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mChildViewCallback = (ViewScrollingCallback) getChildAt(0);
        mChildView = getChildAt(0);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        mScrollingHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public int getNestedScrollAxes() {
        return mScrollingHelper.getNestedScrollAxes();
    }

    @Override
    public void onStopNestedScroll(View child) {
        mScrollingHelper.onStopNestedScroll(child);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed,
                               int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        final View child = target;
        if (dx > 0) {
            if (child.getRight() + dx > getWidth()) {
                dx = child.getRight() + dx - getWidth();
                offsetLeftAndRight(dx);
                consumed[0] += dx;
            }
        } else {
            if (child.getLeft() + dx < 0) {
                dx = child.getLeft() + dx;
                offsetLeftAndRight(dx);
                consumed[0] += dx;
            }
        }

        if (dy > 0) {
            if (child.getBottom() + dy > getHeight()) {
                dy = child.getBottom() + dy - getHeight();
                offsetTopAndBottom(dy);
                consumed[1] += dy;
            }
        } else {
            if (child.getTop() + dy < 0) {
                dy = child.getTop() + dy;
                offsetTopAndBottom(dy);
                consumed[1] += dy;
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("tifone", "parent onTouchEvent");
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (x - mDownX);
                int dy = (int) (y - mDownY);
                offsetLeftAndRight(dx);
                offsetTopAndBottom(dy);
                if (mChildViewCallback != null) {
                    mChildViewCallback.onOffsetChanged(dx, dy);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;

        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }
}
