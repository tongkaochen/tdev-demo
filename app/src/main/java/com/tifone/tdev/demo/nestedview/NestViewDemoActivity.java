package com.tifone.tdev.demo.nestedview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tifone.tdev.demo.R;

public class NestViewDemoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nested_view_layout);
    }
}
