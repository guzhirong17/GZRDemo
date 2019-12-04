package com.gzr.gzrdemo;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.gzr.gzrdemo.util.DensityUtil;
import com.gzr.gzrdemo.util.ScreenUtil;

import org.litepal.LitePalApplication;

public class MyApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //记录异常日志
        MyCrashHandler crashHandler = MyCrashHandler.getInstance();
        crashHandler.setCustomCrashHanler(getApplicationContext());
        //获取设备信息
        printDevInfo();

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    private void printDevInfo() {
        DensityUtil mDensity = new DensityUtil(this);
        System.out.println("-------device info start-------------");
        System.out.println("the device dpi:" + DensityUtil.getDmDensityDpi());
        System.out.println("the device density scale:"
                + DensityUtil.getDmDensithScale());
        System.out.println("screen width:" + ScreenUtil.getScreenWidth(this));
        System.out.println("screen height:" + ScreenUtil.getScreenHeight(this));
        System.out.println("screen status bar height:"
                + ScreenUtil.getStatusHeight(this));
        System.out.println("-------device info end-------------");
    }
}
