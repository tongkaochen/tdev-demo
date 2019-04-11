package com.tifone.tdev.demo.fm.channel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

public class ChannelPagerAdapter extends PagerAdapter {
    private List<ChannelList> mDateSet;
    public ChannelPagerAdapter(List<ChannelList> dateSet) {
        mDateSet = dateSet;
    }

    @Override
    public int getCount() {
        return mDateSet.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        RecyclerView recyclerView = mDateSet.get(position).getRecyclerView();
        ViewGroup parent = (ViewGroup) recyclerView.getParent();
        if (parent != null) {
            parent.removeView(recyclerView);
        }
        container.addView(recyclerView);
        return recyclerView;
    }
    public ChannelList getItem(int position) {
        return mDateSet.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(mDateSet.get(position).getRecyclerView());
    }
}
