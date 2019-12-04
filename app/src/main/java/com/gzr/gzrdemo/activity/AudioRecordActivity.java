package com.gzr.gzrdemo.activity;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gzr.gzrdemo.R;
import com.gzr.gzrdemo.util.LogUtil;
import com.gzr.gzrdemo.util.PcmToWavUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.gzr.gzrdemo.config.GlobalConfig.AUDIO_FORMAT;
import static com.gzr.gzrdemo.config.GlobalConfig.CHANNEL_CONFIG;
import static com.gzr.gzrdemo.config.GlobalConfig.SAMPLE_RATE_INHZ;

public class AudioRecordActivity extends BaseActivity {
    public static final String TAG = "AudioRecordActivity";
    private Button start;
    private AudioRecord audioRecord;
    private boolean isRecording;
    private String filePath = Environment.getExternalStorageDirectory()
            + File.separator + "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audiorecord);

        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("start".equals(start.getText())){
                    start.setText("stop");
                    startRecord();
                }else if("stop".equals(start.getText())){
                    start.setText("start");
                    stopRecord();
                }
            }
        });
    }

    public void startRecord() {
        final int minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_INHZ, CHANNEL_CONFIG, AUDIO_FORMAT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_INHZ,
                CHANNEL_CONFIG, AUDIO_FORMAT, minBufferSize);

        final byte data[] = new byte[minBufferSize];
        final File file = new File(filePath, "test.pcm");
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        if (file.exists()) {
            file.delete();
        }

        audioRecord.startRecording();
        isRecording = true;

        // TODO: 2018/3/10 pcm数据无法直接播放，保存为WAV格式。

        new Thread(new Runnable() {
            @Override
            public void run() {

                FileOutputStream os = null;
                try {
                    os = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (null != os) {
                    while (isRecording) {
                        int read = audioRecord.read(data, 0, minBufferSize);
                        // 如果读取音频数据没有出现错误，就将数据写入到文件
                        if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                            try {
                                os.write(data);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    try {
                        Log.i(TAG, "run: close file output stream !");
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void stopRecord() {
        isRecording = false;
        // 释放资源
        if (null != audioRecord) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
            //recordingThread = null;
            PcmToWavUtil pcmToWavUtil = new PcmToWavUtil(SAMPLE_RATE_INHZ, CHANNEL_CONFIG, AUDIO_FORMAT);
            File pcmFile = new File(filePath, "test.pcm");
            File wavFile = new File(filePath, "test.wav");
            if (!wavFile.mkdirs()) {
                Log.e(TAG, "wavFile Directory not created");
            }
            if (wavFile.exists()) {
                wavFile.delete();
            }
            pcmToWavUtil.pcmToWav(pcmFile.getAbsolutePath(), wavFile.getAbsolutePath());
        }
    }
}
