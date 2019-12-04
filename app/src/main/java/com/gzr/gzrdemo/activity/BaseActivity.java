package com.gzr.gzrdemo.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gzr.gzrdemo.R;
import com.gzr.gzrdemo.util.LogUtil;
import com.gzr.gzrdemo.util.MyActivityManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    //全部权限
    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };
    //存储权限
    protected String[] writePermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    private static final int PERMISSON_REQUESTCODE = 0;
    private boolean isNeedCheck = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isSdkVersion()) {
            if (isWritePermissions()) {
                LogUtil.isWritePermissions = true;
            } else {
                LogUtil.isWritePermissions = false;
            }
        }
        LogUtil.log(TAG, "baseactivity onCreate");
        pushToStack(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.log(TAG, "baseactivity onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.log(TAG, "baseactivity onResume");
        if (Build.VERSION.SDK_INT >= 23
                && getApplicationInfo().targetSdkVersion >= 23) {
            if (isNeedCheck) {
                checkPermissions(needPermissions);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.log(TAG, "baseactivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.log(TAG, "baseactivity onPause");
    }

    private void checkPermissions(String... permissions) {
        try {
            if (Build.VERSION.SDK_INT >= 23
                    && getApplicationInfo().targetSdkVersion >= 23) {
                List<String> needRequestPermissonList = findDeniedPermissions(permissions);
                if (null != needRequestPermissonList
                        && needRequestPermissonList.size() > 0) {
                    String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
                    Method method = getClass().getMethod("requestPermissions", new Class[]{String[].class, int.class});

                    method.invoke(this, array, PERMISSON_REQUESTCODE);

                }
            }
        } catch (Throwable e) {
        }
    }

    //判断版本是否大于6.0
    public boolean isSdkVersion() {
        return Build.VERSION.SDK_INT >= 23
                && getApplicationInfo().targetSdkVersion >= 23;
    }

    //判断是否有读写权限
    public boolean isWritePermissions() {
        List<String> writeRequestPermissonList = findDeniedPermissions(writePermissions);
        if(writeRequestPermissonList.size() > 0){
            return false;
        }
        return true;
    }

    //逐个判断是否还有未通过的权限
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        if (Build.VERSION.SDK_INT >= 23
                && getApplicationInfo().targetSdkVersion >= 23) {
            try {
                for (int i = 0; i < permissions.length; i++) {
                    if (ContextCompat.checkSelfPermission(this, permissions[i]) !=
                            PackageManager.PERMISSION_GRANTED) {
                        needRequestPermissonList.add(permissions[i]);//添加还未授予的权限到mPermissionList中
                    }
                }
            } catch (Throwable e) {

            }
        }
        return needRequestPermissonList;
    }

    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
                isNeedCheck = false;
            }
        }
    }

    private void showMissingPermissionDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限，应用暂时无法正常使用。请单击【设置】按钮前往设置中心进行权限授权。");
        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.setPositiveButton("设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mAlertController);
            mTitleView.setTextColor(getResources().getColor(R.color.green));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    public void pushToStack(Activity activity) {
        MyActivityManager.getActivityManager().push(activity);
    }

    public void popFromStack(Activity activity) {
        MyActivityManager.getActivityManager().pop(activity);
    }
}
