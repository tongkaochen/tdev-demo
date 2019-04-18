package com.tifone.tdev.demo.component.seekbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tifone.tdev.demo.R;

public class ProgressTipsSeekBarContainer implements SeekBar.OnSeekBarChangeListener {
    private final ViewGroup mProgressTipsLayout;
    private final TextView mProgressTv;
    private Context mContext;
    private SeekBar mSeekBar;
    private View mRoot;
    private boolean mVisibility;
    private ValueAnimator mAnimator;
    private SeekBar.OnSeekBarChangeListener mListener;

    public ProgressTipsSeekBarContainer(Context context) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        mRoot = inflater.inflate(R.layout.progress_tips_layout, null);
        mSeekBar = mRoot.findViewById(R.id.seek_bar);
        mSeekBar.setOnSeekBarChangeListener(this);

        mProgressTv = mRoot.findViewById(R.id.progress_tv);
        mProgressTipsLayout = mRoot.findViewById(R.id.progress_tips_layout);
        mProgressTipsLayout.setVisibility(View.INVISIBLE);
    }
    public void setOnSeekBarChangedListenner(SeekBar.OnSeekBarChangeListener listener) {
        mListener = listener;
    }
    public SeekBar getSeekBar() {
        return mSeekBar;
    }
    public void attachTo(ViewGroup parent) {
        if (parent == null) {
            return;
        }
        ViewGroup seekRootParent = (ViewGroup) mRoot.getParent();
        if (seekRootParent != null) {
            seekRootParent.removeView(mRoot);
        }
        parent.addView(mRoot);
    }

    private float getLocation(SeekBar seekBar) {
        float progressRatio = (float) seekBar.getProgress() / seekBar.getMax();
        int seekBarWidth = seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
        int thumbWidth = seekBar.getThumb().getIntrinsicWidth() - seekBar.getThumbOffset();
        return seekBar.getX() + seekBarWidth * progressRatio;
    }
    private void updateTipsViewDisplay(boolean visibility) {
        mProgressTipsLayout.setPivotX(mProgressTipsLayout.getWidth() / 2.0f);
        mProgressTipsLayout.setPivotY(mProgressTipsLayout.getHeight());
        float location = getLocation(mSeekBar);

        mProgressTv.setText(String.valueOf(mSeekBar.getProgress()));
        if (mVisibility != visibility) {
            applyAnimation(visibility);
        }
        mProgressTipsLayout.setX(location);
    }

    private void applyAnimation(final boolean show) {
        float begin = show ? 0f : 1f;
        float end = show ? 1f : 0f;
        ValueAnimator animator = ValueAnimator.ofFloat(begin, end);
        if (mAnimator == null || mVisibility != show) {
            mAnimator = animator;
        }
        mAnimator.cancel();
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mProgressTipsLayout.setScaleX(value);
                mProgressTipsLayout.setScaleY(value);
            }
        });
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                if (!show) {
                    mProgressTipsLayout.setVisibility( View.INVISIBLE);
                    mVisibility = false;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!show) {
                    mProgressTipsLayout.setVisibility( View.INVISIBLE);
                    mVisibility = false;
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (show) {
                    mProgressTipsLayout.setVisibility(View.VISIBLE);
                    mVisibility = true;
                }
            }
        });
        mAnimator.setDuration(200);
        mAnimator.start();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mListener != null) {
            mListener.onProgressChanged(seekBar, progress, fromUser);
        }
        seekBar.removeCallbacks(runnable);
        updateTipsViewDisplay(true);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (mListener != null) {
            mListener.onStartTrackingTouch(seekBar);
        }
        seekBar.removeCallbacks(runnable);
        updateTipsViewDisplay(true);
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateTipsViewDisplay(false);
        }
    };

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mListener != null) {
            mListener.onStopTrackingTouch(seekBar);
        }
        seekBar.removeCallbacks(runnable);
        seekBar.postDelayed(runnable, 800);
    }
}
