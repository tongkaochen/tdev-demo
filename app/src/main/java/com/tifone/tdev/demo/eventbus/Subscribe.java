package com.tifone.tdev.demo.eventbus;

import java.util.List;

public interface Subscribe {
    void onEvent(EventMessage message);
    List<Integer> registerType();
}
