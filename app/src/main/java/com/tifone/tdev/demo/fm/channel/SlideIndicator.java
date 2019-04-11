package com.tifone.tdev.demo.fm.channel;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tifone.tdev.demo.R;

import java.util.ArrayList;
import java.util.List;

public class SlideIndicator extends FrameLayout{

    private static final int ANIMATION_DURATION = 1000;
    private View mIndicatorItem;
    private List<Item> items = new ArrayList<>();
    private float currentLocation;
    private int currentWidth;

    public SlideIndicator(@NonNull Context context) {
        this(context, null);
    }

    public SlideIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        View indicatorLayout = LayoutInflater.from(getContext())
                .inflate(R.layout.indicator_layout, this);
        mIndicatorItem = indicatorLayout.findViewById(R.id.indicator_item);
    }

    public void locationChanged(int from, int to) {
        Item fromItem = items.get(from);
        Item toItem = items.get(to);
        if (fromItem == null || toItem == null) {
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(mIndicatorItem, "translationX",
                currentLocation, toItem.start);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentLocation = (float) animation.getAnimatedValue();
            }
        });
        animator.setDuration(ANIMATION_DURATION);
        animator.start();

        ValueAnimator widthAnimation = ValueAnimator.ofInt(currentWidth, toItem.width);
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int width = (Integer) animation.getAnimatedValue();
                setIndicatorWidth(width);
                currentWidth = width;
            }
        });
        widthAnimation.setDuration(ANIMATION_DURATION);
        widthAnimation.start();
    }

    public void setData(List<Item> headerItems) {
        items = headerItems;
    }

    private void setIndicatorWidth(int width) {
        final ViewGroup.LayoutParams params = mIndicatorItem.getLayoutParams();
        params.width = width;
        mIndicatorItem.setLayoutParams(params);
    }

    public int size() {
        return items.size();
    }

    public void currentPosition(int position) {
        Item currentItem = items.get(position);
        mIndicatorItem.setTranslationX(currentItem.start);
        currentLocation = currentItem.start;
        setIndicatorWidth(currentItem.width);
        currentWidth = currentItem.width;
    }

    static class Item {
        float start;
        float end;
        int width;
        Item(float start, float end, int width) {
            this.start = start;
            this.end = end;
            this.width = width;
        }
    }
}
