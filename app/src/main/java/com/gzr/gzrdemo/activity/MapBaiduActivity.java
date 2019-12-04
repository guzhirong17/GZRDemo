package com.gzr.gzrdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baidu.mapapi.map.MapView;
import com.gzr.gzrdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapBaiduActivity extends BaseActivity {
    public static final String TAG = "MapBaiduActivity";
    @BindView(R.id.bmapView)
    MapView bmapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_baidu);
        ButterKnife.bind(this);

        bmapView = (MapView) findViewById(R.id.bmapView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        bmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        bmapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        bmapView.onDestroy();
    }
}
