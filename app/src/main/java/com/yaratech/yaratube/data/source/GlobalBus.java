package com.yaratech.yaratube.data.source;

import org.greenrobot.eventbus.EventBus;

public class GlobalBus {
    private static EventBus INSTANCE = null;

    public static EventBus getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = EventBus.getDefault();
        }
        return INSTANCE;
    }
}
