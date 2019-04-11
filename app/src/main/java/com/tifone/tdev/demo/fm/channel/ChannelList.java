package com.tifone.tdev.demo.fm.channel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tifone.tdev.demo.R;

import java.util.ArrayList;
import java.util.List;

public class ChannelList {
    public static final int TYPE_ALL = 1;
    public static final int TYPE_FAVOURITE = 2;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private int mType;
    private RecyclerView.Adapter mAdapter;
    private List<DataItem> mDataList;
    public ChannelList(Context context, int type) {
        mContext = context;
        mType = type;
        init();
    }
    private void init() {
        mDataList = new ArrayList<>();
        View view = LayoutInflater.from(mContext).inflate(R.layout.channel_recycler_view, null);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        createAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }
    private void buildData() {
        switch (mType) {
            case TYPE_ALL:
                buildAllData();
                break;
            case TYPE_FAVOURITE:
                buildFavouriteData();
                break;
        }
    }
    private void buildAllData() {
        mDataList.clear();
        mDataList.add(new DataItem("85.4", false));
        mDataList.add(new DataItem("86.4", false));
        mDataList.add(new DataItem("87.4", false));
        mDataList.add(new DataItem("88.4", true));
        mDataList.add(new DataItem("89.4", true));
        mDataList.add(new DataItem("90.4", false));
        mDataList.add(new DataItem("100.4", false));
        mDataList.add(new DataItem("85.4", false));
        mDataList.add(new DataItem("85.4", false));
        mDataList.add(new DataItem("85.4", false));
        mDataList.add(new DataItem("85.4", false));
        mDataList.add(new DataItem("85.4", false));
        mDataList.add(new DataItem("85.4", false));
        mDataList.add(new DataItem("85.4", false));
        mDataList.add(new DataItem("85.4", false));
        mDataList.add(new DataItem("85.4", false));
        mDataList.add(new DataItem("85.4", false));
        mDataList.add(new DataItem("85.4", false));
        mDataList.add(new DataItem("85.4", false));
        mDataList.add(new DataItem("86.4", false));
    }
    private void buildFavouriteData() {
        mDataList = getFavouriteData();
    }
    private List<DataItem> getFavouriteData() {
        buildAllData();
        List<DataItem> favourites = new ArrayList<>();
        for (DataItem item : mDataList) {
            if (item.isLike) {
                favourites.add(item);
            }
        }
        return favourites;
    }

    private void createAdapter() {
        buildData();
        mAdapter = new ChannelAdapter();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    class ChannelAdapter extends RecyclerView.Adapter<ChannelHolder> {

        @NonNull
        @Override
        public ChannelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.channel_item, null);

            return new ChannelHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChannelHolder channelHolder, int i) {
            channelHolder.title.setText(mDataList.get(i).name);
            channelHolder.like.setChecked(mDataList.get(i).isLike);
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }
    }

    class ChannelHolder extends RecyclerView.ViewHolder {
        TextView title;
        CheckBox like;
        public ChannelHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            like = itemView.findViewById(R.id.like);
        }
    }

    public boolean isScrollToTop() {
        LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        return llm.findFirstCompletelyVisibleItemPosition() < 1;
    }
    public boolean isScrollToBottom() {
        LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        return llm.findLastCompletelyVisibleItemPosition()
                >= mRecyclerView.getAdapter().getItemCount() - 1;
    }

}
