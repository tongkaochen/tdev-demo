package com.tifone.tdev.demo.blurimg;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tifone.tdev.demo.R;

public class BlurredBackgroundLayout extends FrameLayout {
    private BlurImageView backgroundImage;
    private ImageView centerCircleImage;
    private Drawable mSrcImage;

    public BlurredBackgroundLayout(Context context) {
        this(context, null);
    }

    public BlurredBackgroundLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurredBackgroundLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(
                attrs, R.styleable.BlurredBackgroundLayout);

        mSrcImage = ta.getDrawable(R.styleable.BlurredBackgroundLayout_src);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.blur_background_layout, this, false);
        backgroundImage = view.findViewById(R.id.background);
        centerCircleImage = view.findViewById(R.id.circle_icon);
        if (mSrcImage != null) {
            backgroundImage.setImageDrawable(mSrcImage);
            centerCircleImage.setImageDrawable(mSrcImage);
        } else {
            backgroundImage.setVisibility(INVISIBLE);
            centerCircleImage.setVisibility(INVISIBLE);
        }
        addView(view);
    }

    public void setDisplayImage(Drawable drawable) {
        setDisplayImage(ImageUtil.translateDrawableToBitmap(drawable));
    }
    public void setDisplayImage(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        backgroundImage.setImageBitmap(bitmap);
        centerCircleImage.setImageBitmap(bitmap);
        backgroundImage.setVisibility(VISIBLE);
        centerCircleImage.setVisibility(VISIBLE);
    }
    public void setDisplayImage(int resId) {
        setDisplayImage(getResources().getDrawable(resId));
    }

    public void setCenterCircleImageVisibility(boolean visibility) {
        if (centerCircleImage == null) {
            return;
        }
        centerCircleImage.setVisibility(visibility ? VISIBLE : GONE);
    }
}
