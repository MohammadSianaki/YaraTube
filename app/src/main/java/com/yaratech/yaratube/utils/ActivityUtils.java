package com.yaratech.yaratube.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.HashMap;

public class ActivityUtils {

    public static final HashMap<String, Fragment> cachedFragments = new HashMap<>();

    public static void addFragmentToActivity(FragmentManager fragmentManager,
                                             Fragment fragment, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }


    public static void addFragmentToActivity(FragmentManager fragmentManager,
                                             Fragment fragment, int frameId, boolean addToBackStack, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();

    }


    public static void checkAndSetRtl(Activity activity) {
        if (activity.getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
            activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

}
