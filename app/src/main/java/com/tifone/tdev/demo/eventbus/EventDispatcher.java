package com.tifone.tdev.demo.eventbus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventDispatcher {
    private Set<Subscribe> mSubscriptions;
    private static EventDispatcher INSTANCE;

    final class Filter {
        public static final int ALL = 0;
    }
    private EventDispatcher() {
        mSubscriptions = new HashSet<>();
    }

    public static EventDispatcher getDefault() {
        if (INSTANCE == null) {
            synchronized (EventDispatcher.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EventDispatcher();
                }
            }
        }
        return INSTANCE;
    }

    public void register(Subscribe subscribe) {
        mSubscriptions.add(subscribe);
    }
    public void post(int type, EventMessage message) {
        for (Subscribe subscription : mSubscriptions) {
            List<Integer> targetTypes = subscription.registerType();
            boolean containType = targetTypes != null && targetTypes.contains(type);
            boolean shouldDispatch = (type == Filter.ALL) || containType;
            if (!shouldDispatch) {
                continue;
            }
            subscription.onEvent(message);
        }
    }
    public void unregister(Subscribe subscribe) {
        mSubscriptions.remove(subscribe);
    }
}
