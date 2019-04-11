package com.tifone.tdev.demo.fm.channel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tifone.tdev.demo.R;

import java.util.ArrayList;
import java.util.List;

public class ChannelListLayout extends LinearLayout implements View.OnClickListener {
    private SlideIndicator mIndicator;
    private TextView mAllChannelTv;
    private TextView mFavouriteChannelTv;
    private View mChannelHeader;
    private ViewPager mViewPager;
    private ChannelPagerAdapter mAdapter;
    private int mLastPosition;

    private int minHeight;
    private int maxHeight;
    private boolean isInitStatus;
    private float lastY;
    private float lastX;
    private float xDistance;

    public ChannelListLayout(Context context) {
        this(context, null);
    }

    public ChannelListLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChannelListLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        mChannelHeader = inflater.inflate(R.layout.channel_header, null);
        mViewPager = (ViewPager) inflater.inflate(R.layout.view_pager, null);
        setupViewPager();
        mIndicator = mChannelHeader.findViewById(R.id.slide_indicator);
        mAllChannelTv = mChannelHeader.findViewById(R.id.all_channel);
        mFavouriteChannelTv = mChannelHeader.findViewById(R.id.favourite_channel);
        setOrientation(VERTICAL);
        addView(mChannelHeader);
        addView(mViewPager);
        mAllChannelTv.setOnClickListener(this);
        mFavouriteChannelTv.setOnClickListener(this);

        minHeight = getScreenHeight() / 2;
        maxHeight = (int) (minHeight * 1.5);
        isInitStatus = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isInitStatus) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(minHeight,
                    MeasureSpec.getMode(heightMeasureSpec));
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        List<SlideIndicator.Item> headerItems = new ArrayList<>();
        int[] location = new int[2];
        mAllChannelTv.getLocationInWindow(location);
        int parentPaddingLeft = mChannelHeader.getPaddingLeft();
        int width = mAllChannelTv.getMeasuredWidth();
        int start = mAllChannelTv.getLeft() - parentPaddingLeft;
        int end = mAllChannelTv.getRight() - parentPaddingLeft;
        Log.d("tifone", " start1 = " + start + " end = " + end);
        headerItems.add(new SlideIndicator.Item(start, end, width));

        mFavouriteChannelTv.getLocationInWindow(location);
        width = mFavouriteChannelTv.getMeasuredWidth();
        start = mFavouriteChannelTv.getLeft() - parentPaddingLeft;
        end = mFavouriteChannelTv.getRight() - parentPaddingLeft;
        Log.d("tifone", " start2 = " + start + " end = " + end);
        headerItems.add(new SlideIndicator.Item(start, end, width));
        mIndicator.setData(headerItems);
        mIndicator.currentPosition(mViewPager.getCurrentItem());
    }

    private void setupViewPager() {
        List<ChannelList> list = new ArrayList<>();
        list.add(ChannelListCreator.createAllChannelList(getContext()));
        list.add(ChannelListCreator.createFavouriteChannelList(getContext()));
        mAdapter = new ChannelPagerAdapter(list);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset,
                                       int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                if (mLastPosition != position
                        && position < mIndicator.size()) {
                    onLocationChanged(mLastPosition, position);
                    mLastPosition = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private void onLocationChanged(int from, int to) {
        mIndicator.locationChanged(from, to);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_channel:
                switchToAllChannel();
                break;
            case R.id.favourite_channel:
                switchToFavouriteChannel();
                break;
        }
    }
    private void switchToAllChannel() {
        mViewPager.setCurrentItem(0, true);
    }
    private void switchToFavouriteChannel() {
        mViewPager.setCurrentItem(1, true);
    }

    public void onHeaderDragged(float diff) {
        isInitStatus = false;
        int currentHeight = getHeight();
        currentHeight += diff;
        if (currentHeight < minHeight) {
            currentHeight = minHeight;
        } else if (currentHeight > maxHeight) {
            currentHeight = maxHeight;
        }
        if (currentHeight == getHeight()) {
            return;
        }
        Log.d("tifone", "currentHeight = " + currentHeight);
        setHeight(currentHeight);
    }
    private void setHeight(int height) {
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = height;
        setLayoutParams(params);
    }

    public void onDragRelease() {
        if (getHeight() == minHeight || getHeight() == maxHeight) {
            return;
        }
        int limitHeight = minHeight + (maxHeight - minHeight) / 2;
        Log.d("tifone", "limitHeight = " + limitHeight);
        if (getHeight() >= limitHeight) {
            animationTo(maxHeight);
        } else {
            animationTo(minHeight);
        }
    }
    private void animationTo(int height) {
        Log.d("tifone", "height = " + height);
        ValueAnimator animator = ValueAnimator.ofInt(getHeight(), height);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setHeight((Integer) animation.getAnimatedValue());
            }
        });
        animator.setDuration(500);
        animator.start();
    }
    private int getScreenHeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics display = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(display);
        return display.heightPixels;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("tifone", "onInterceptTouchEvent action= " + ev.getAction());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = ev.getRawX();
                lastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float diffY = ev.getRawY() - lastY;
                float diffX = ev.getRawX() - lastX;
                Log.d("tifone", "diffx = " + diffX);
                Log.d("tifone", "onInterceptTouchEvent xDistance" + xDistance);
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    break;
                }
                if (diffY > 0) {
                    if (isListScrollToTop()) {
                        Log.d("tifone", "onInterceptTouchEvent intercept");
                        return true;
                    }
                } else  {
                    if (isListScrollToBottom() && !isListScrollToTop()) {
                        Log.d("tifone", "onInterceptTouchEvent intercept");
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                xDistance = 0;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.d("tifone", " dispatchTouchEvent = " + ev.getAction());
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            requestDisallowInterceptTouchEvent(false);
        }
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            float diffY = ev.getRawY() - lastY;
            float diffX = ev.getRawX() - lastX;
            Log.d("tifone", "diffx = " + diffX);
            if (Math.abs(diffX) > Math.abs(diffY)) {
                return super.dispatchTouchEvent(ev);
            }
            if (diffY > 0) {
                if (isListScrollToTop()) {
                    Log.d("tifone", "dispatchTouchEvent dispatch");
                    return super.dispatchTouchEvent(ev);
                }
            } else  {
                if (isListScrollToBottom()) {
                    Log.d("tifone", "dispatchTouchEvent dispatch2");
                    return super.dispatchTouchEvent(ev);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean needIntercept() {
        ChannelList current = mAdapter.getItem(mViewPager.getCurrentItem());
        return current.isScrollToBottom() || current.isScrollToTop();
    }
    private boolean isListScrollToTop() {
        ChannelList current = mAdapter.getItem(mViewPager.getCurrentItem());
        return current.isScrollToTop();
    }
    private boolean isListScrollToBottom() {
        ChannelList current = mAdapter.getItem(mViewPager.getCurrentItem());
        return current.isScrollToBottom();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("tifone", "action = " + event.getAction());
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float currentY = event.getRawY();
            float diff = currentY - lastY;
            onHeaderDragged(-diff);
            lastY = currentY;
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            onDragRelease();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public boolean requestScrollToBottom() {
        if (getHeight() == maxHeight) {
            animationTo(minHeight);
            return true;
        }
        return false;
    }
}
