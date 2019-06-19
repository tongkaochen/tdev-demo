package com.tifone.tdev.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tifone.tdev.demo.blurimg.BlurImageDemoActivity;
import com.tifone.tdev.demo.component.seekbar.SeekBarLayoutActivity;
import com.tifone.tdev.demo.eventbus.EventBusDemoActivity;
import com.tifone.tdev.demo.fm.channel.ChannelDemoActivity;
import com.tifone.tdev.demo.gflipui.GflipUiDemoActivity;
import com.tifone.tdev.demo.nestedview.NestViewDemoActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private List<Target> mTargetList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private final int focusIndex = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.demo_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(createAdapter());

    }
    private DemoAdapter createAdapter() {
        buildTargets();
        return new DemoAdapter(mTargetList);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void buildTargets() {
        addTarget("Tips SeekBar", SeekBarLayoutActivity.class);
        addTarget("FM channel demo", ChannelDemoActivity.class); // 1
        addTarget("Nested scroll view", NestViewDemoActivity.class);
        addTarget("Blur demo", BlurImageDemoActivity.class);
        addTarget("EventBus", EventBusDemoActivity.class);
        addTarget("Gfilp ui demo", GflipUiDemoActivity.class); // 5
    }
    private void addTarget(String name, Class clazz) {
        mTargetList.add(createTarget(name, clazz));
    }
    private Target createTarget(String name, Class clazz) {
        return new Target(name, clazz);
    }
    class DemoAdapter extends RecyclerView.Adapter<MyHolder> {
        private List<Target> mTargets;
        DemoAdapter(List<Target> demoTargets) {
            mTargets = demoTargets;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.demo_item, null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {
            Log.d("tifone", "bindview : " + i);
            final Target target = mTargets.get(i);
            myHolder.button.setText(target.name);
            myHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(v.getContext(), target.targetActivityClass);
                    v.getContext().startActivity(intent);
                }
            });
            if (i == focusIndex) {
                myHolder.button.performClick();
            }
        }

        @Override
        public int getItemCount() {
            return mTargets.size();
        }
    }
    class MyHolder extends RecyclerView.ViewHolder {
        Button button;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.demo_item_btn);
        }
    }
    class Target {
        final String name;
        final Class targetActivityClass;
        Target(String name, Class clazz) {
            this.name = name;
            targetActivityClass = clazz;
        }
    }
}
