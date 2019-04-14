package com.tifone.tdev.demo.component.seekbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tifone.tdev.demo.R;

/**
 * Create by Tifone on 2019/4/14.
 */
public class SeekBarLayoutActivity extends Activity implements SeekBar.OnSeekBarChangeListener {
    private SeekBar mSeekBar;
    private TextView mProgressShowTv;
    private FrameLayout mProgressTipsLayout;
    private boolean mVisibility;
    private ValueAnimator mAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seekbar_layout);
        mSeekBar = findViewById(R.id.demo_seek_bar);
        mProgressShowTv = findViewById(R.id.progress_show_tv);
        mProgressTipsLayout =findViewById(R.id.progress_tips_layout);
        mSeekBar.setOnSeekBarChangeListener(this);
        mProgressTipsLayout.setVisibility(View.INVISIBLE);
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

        mProgressShowTv.setText(String.valueOf(mSeekBar.getProgress()));
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
        seekBar.removeCallbacks(runnable);
        updateTipsViewDisplay(true);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
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
        seekBar.removeCallbacks(runnable);
        seekBar.postDelayed(runnable, 800);
    }
}
