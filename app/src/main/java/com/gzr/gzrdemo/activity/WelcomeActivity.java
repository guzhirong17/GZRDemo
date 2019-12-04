package com.gzr.gzrdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gzr.gzrdemo.Constants;
import com.gzr.gzrdemo.MainActivity;
import com.gzr.gzrdemo.util.MyActivityManager;
import com.gzr.gzrdemo.util.SharePreferenceUtil;

public class WelcomeActivity extends BaseActivity{
    public static final String TAG = "WelcomeActivity";
    private Context mContext;
    private boolean isAlreadyShowGuide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        isAlreadyShowGuide = (boolean) SharePreferenceUtil.get(mContext,
                Constants.LAUNCH_GUIDE, false);
        if(!isAlreadyShowGuide){
            startActivity(new Intent(this, LaunchGuideActivity.class));
            MyActivityManager.getActivityManager().pop((Activity) mContext);
        }else{
            startActivity(new Intent(this, MainActivity.class));
            MyActivityManager.getActivityManager().pop((Activity) mContext);
        }
    }
}
