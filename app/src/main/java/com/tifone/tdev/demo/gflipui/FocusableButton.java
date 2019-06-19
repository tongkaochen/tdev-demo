package com.tifone.tdev.demo.gflipui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.tifone.tdev.demo.R;

public class FocusableButton extends Button {
    public FocusableButton(Context context) {
        this(context, null);
    }

    public FocusableButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusableButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(true);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setTextColor(getContext().getColor(
                focused ? R.color.color_white : R.color.color_black));
    }

}
