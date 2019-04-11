package com.tifone.tdev.demo.fm.channel;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tifone.tdev.demo.R;

public class ChannelDemoActivity extends Activity {

    private ChannelListLayout mLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_demo_layout);
        mLayout = findViewById(R.id.channel_layout);
    }

    @Override
    public void onBackPressed() {
        if (mLayout.requestScrollToBottom()) {
            return;
        }
        super.onBackPressed();
    }
}
