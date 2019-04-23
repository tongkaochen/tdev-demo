package com.tifone.tdev.demo.blurimg;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tifone.tdev.demo.R;

public class BlurredBackgroundLayout extends FrameLayout {
    private BlurImageView background;
    private ImageView centerCircleImage;

    public BlurredBackgroundLayout(Context context) {
        super(context);
    }

    public BlurredBackgroundLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlurredBackgroundLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.blur_background_layout, this, false);
        background = view.findViewById(R.id.background);
        centerCircleImage = view.findViewById(R.id.circle_icon);
        if (centerCircleImage.getDrawable() != null) {
            background.setImageDrawable(centerCircleImage.getDrawable());
        }
        addView(view);
    }
    public void setCenterCircleImageVisibility(boolean visibility) {
        if (centerCircleImage == null) {
            return;
        }
        centerCircleImage.setVisibility(visibility ? VISIBLE : GONE);
    }
}
