package com.tifone.tdev.demo.eventbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tifone.tdev.demo.eventbus.EventDispatcher.Filter;

import java.util.ArrayList;
import java.util.List;

public class EventBusDemoActivity extends Activity implements Subscribe {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventDispatcher.getDefault().register(this);
    }

    @Override
    public void onEvent(EventMessage message) {
        String msg = message.getMessage();
    }

    @Override
    public List<Integer> registerType() {
        List<Integer> types = new ArrayList<>();
        types.add(Filter.ALL);
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventDispatcher.getDefault().post(Filter.ALL,
                new EventMessage("onPause"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventDispatcher.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
