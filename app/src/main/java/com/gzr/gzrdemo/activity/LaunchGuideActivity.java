package com.gzr.gzrdemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.gzr.gzrdemo.R;
import com.gzr.gzrdemo.adapter.GuideAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchGuideActivity extends BaseActivity {
    public static final String TAG = "LaunchGuideActivity";

    @BindView(R.id.guide_vp)
    ViewPager mGuideVp;
    private Context mContext;

    private static final int[] GUIDE_RESOURCES = {
            R.mipmap.ic_launch_guide1, R.mipmap.ic_launch_guide2, R.mipmap.ic_launch_guide3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_guide);
        ButterKnife.bind(this);
        mContext = this;
        mGuideVp.setAdapter(new GuideAdapter(this, GUIDE_RESOURCES));
    }
}
