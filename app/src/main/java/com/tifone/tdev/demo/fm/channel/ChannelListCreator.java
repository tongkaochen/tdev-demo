package com.tifone.tdev.demo.fm.channel;

import android.content.Context;

public class ChannelListCreator {
    public static ChannelList createAllChannelList(Context context) {
        return new ChannelList(context, ChannelList.TYPE_ALL);
    }
    public static ChannelList createFavouriteChannelList(Context context) {
        return new ChannelList(context, ChannelList.TYPE_FAVOURITE);
    }
}
