package com.gzr.gzrdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gzr.gzrdemo.R;

public class LoginActivity extends BaseActivity {
    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
}
