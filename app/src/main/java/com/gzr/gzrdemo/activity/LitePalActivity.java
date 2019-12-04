package com.gzr.gzrdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gzr.gzrdemo.R;
import com.gzr.gzrdemo.model.UserInfoData;
import com.gzr.gzrdemo.util.LogUtil;

import org.litepal.LitePal;

import java.util.List;

public class LitePalActivity extends BaseActivity{
    public static final String TAG = "LitePalActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepal);

        UserInfoData info = new UserInfoData();
        info.setUsername("gzr");
        info.setPassword("123");
        info.save();

        List<UserInfoData> infos = LitePal.findAll(UserInfoData.class);
        LogUtil.log(TAG,""+infos.size());
    }
}
