package com.gzr.gzrdemo;

import android.content.Intent;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gzr.gzrdemo.activity.AudioRecordActivity;
import com.gzr.gzrdemo.activity.BaseActivity;
import com.gzr.gzrdemo.activity.LitePalActivity;
import com.gzr.gzrdemo.activity.MapBaiduActivity;
import com.gzr.gzrdemo.model.UserInfoData;
import com.gzr.gzrdemo.util.LogUtil;

import org.litepal.LitePal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.gzr.gzrdemo.config.GlobalConfig.AUDIO_FORMAT;
import static com.gzr.gzrdemo.config.GlobalConfig.CHANNEL_CONFIG;
import static com.gzr.gzrdemo.config.GlobalConfig.SAMPLE_RATE_INHZ;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    public static final String TAG = "MainActivity";
    private Button audiorecord;
    private Button litepal;
    private Button map_baidu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audiorecord = findViewById(R.id.audiorecord);
        audiorecord.setOnClickListener(this);

        litepal = findViewById(R.id.litepal);
        litepal.setOnClickListener(this);

        map_baidu = findViewById(R.id.map_baidu);
        map_baidu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if(v.getId() == R.id.audiorecord){
            intent = new Intent(MainActivity.this, AudioRecordActivity.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.litepal){
            intent = new Intent(MainActivity.this, LitePalActivity.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.map_baidu){
            intent = new Intent(MainActivity.this, MapBaiduActivity.class);
            startActivity(intent);
        }
    }
}
