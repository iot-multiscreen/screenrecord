package com.base.module.h264player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class MainActivity extends Activity {
    private final static String TAG = "h264player.MainActivity";

    private String h264Path = "/mnt/sdcard/Download/test.h264";
    private File h264File = new File(h264Path);
    private SurfaceView mSurfaceView;
    private Button mReadButton;
    private MediaCodecUtil mediaCodecUtil;
    private MediacodecThread mediacodecThread;
    private boolean isInit = false;//the mediacode thread only one

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        intLayout();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediacodecThread.interrupt();
    }

    private void intLayout(){
        mSurfaceView = (SurfaceView) findViewById(R.id.playersurface);
        mReadButton = (Button) findViewById(R.id.startplayer);
        mReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (h264File.exists()) {
                    if(!isInit) {
                        mediacodecThread = new MediacodecThread(mediaCodecUtil, h264Path);
                        mediacodecThread.setEventHandler(mEventHandler);
                        mediacodecThread.start();
                        isInit = true;
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "H264 file not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if(null == mediaCodecUtil) {
                    mediaCodecUtil = new MediaCodecUtil(surfaceHolder);
                    mediaCodecUtil.startCodec(MediaFormat.MIMETYPE_VIDEO_AVC);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                mediacodecThread.stopThread();
            }
        });
    }

    Handler mEventHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what){
                case MediaCodecUtil.PLAYSTOP:
                    isInit = false ;
                    mediacodecThread.stopThread();
                    mediacodecThread = null;
                    Log.d(TAG,"PLAYSTOP");
                    break;
            }
            return false;
        }
    });

}
