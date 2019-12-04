package com.gzr.gzrdemo.util;

import android.app.Activity;

import java.util.Stack;

public class MyActivityManager {
    private static final String TAG = "MyActivityManager";
    private static MyActivityManager mInstance = new MyActivityManager();
    private static Stack<Activity> mActivityStack;

    public static MyActivityManager getActivityManager(){
        return mInstance;
    };

    public void push(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        if (activity == null) {
            return;
        }
        LogUtil.log(TAG, "push activity:" + activity);
        mActivityStack.add(activity);
    }

    public Activity getTop() {
        if (mActivityStack != null && mActivityStack.size() > 0) {
            return mActivityStack.lastElement();
        }
        return null;
    }

    public void pop(Activity activity) {
        if (mActivityStack != null && mActivityStack.size() > 0) {
            if (activity != null) {
                activity.finish();

                LogUtil.log(TAG, "pop activity:" + activity);
                mActivityStack.remove(activity);
                activity = null;
            }
        }
    }

    public void popAll() {
        if (mActivityStack != null) {
            while (mActivityStack.size() > 0) {
                Activity activity = getTop();
                pop(activity);
            }
        }
    }
}
