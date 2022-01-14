package com.base.module.mediaplayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import com.base.module.meidiaplayer.R;

public class MainActivity extends Activity {
    private final static String TAG = "player.MainActivity";
    private Button android_player,android_ndk_player,third_sdk_player;
    private Button mPlay,mPause,mStop;
    private Button mNative_test;
    private TextureView mVideoView;
    private MeidaplayerTest mediaplayer;
    private boolean mPlayerEnable = false;
    private boolean mPlayerPlay = false;
    private Context mContext;
    private Surface mSurface;
    private MediaplayerEngine mediaplayerEngine;
    private final static String TESTVIDEO = "/mnt/sdcard/Download/testvideo.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
    }

    private View.OnClickListener android_play_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mediaplayer == null){
                mediaplayer = new MeidaplayerTest(MeidaplayerTest.ANDROID_PLAYER,mContext);
                mediaplayer.setDatatSource(TESTVIDEO);
                mediaplayer.setSurface(mSurface);
                mediaplayer.setEventHandler(mEventHandler);
                mediaplayer.init();
            }
        }
    };
    private View.OnClickListener ndk_player_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    private View.OnClickListener third_sdk_player_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    private View.OnClickListener mPlay_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if((mPlayerEnable) &&(!mPlayerPlay)&&(mediaplayer != null)){
                mediaplayer.start();
                mPlayerPlay = true;
            }
        }
    };
    private View.OnClickListener mPause_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if((mPlayerEnable) &&(mPlayerPlay)){
                mediaplayer.pause();
                mPlayerPlay = false;
            }
        }
    };
    private View.OnClickListener mStop_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mPlayerEnable){
                mediaplayer.stop();
                mediaplayer.release();
                mediaplayer = null;
                mPlayerPlay = false;
            }
        }
    };
    private View.OnClickListener mNativetest_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mediaplayerEngine == null){
                mediaplayerEngine = new MediaplayerEngine();
            }
            mediaplayerEngine.nativeInit(0);
        }
    };

    private Handler mEventHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG,"msg.what="+msg.what);
            switch(msg.what){
                case MediaplayerMSG.PLAYER_PREPARED:
                    mPlayerEnable = true;
                    break;
                case MediaplayerMSG.VIDEOSIZECHANGED:
                    break;
                default:
                    break;
            }
        }
    };
    private TextureView.SurfaceTextureListener mVideoView_listener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int w, int h) {
            Log.d(TAG,"onSurfaceTextureAvailable w=" + w + " h=" + h);

            mSurface = new Surface(surfaceTexture);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };
    private void initlayout(){
        android_player = (Button) findViewById(R.id.android_player);
        android_ndk_player = (Button) findViewById(R.id.android_ndk_player);
        third_sdk_player = (Button) findViewById(R.id.sdk_player);
        mVideoView = (TextureView) findViewById(R.id.videoView);
        mPlay = (Button)findViewById(R.id.play);
        mPause = (Button)findViewById(R.id.pause);
        mStop = (Button)findViewById(R.id.stop);
        mNative_test = (Button)findViewById(R.id.native_test);

        android_player.setOnClickListener(android_play_listener);
        android_ndk_player.setOnClickListener(ndk_player_listener);
        third_sdk_player.setOnClickListener(third_sdk_player_listener);
        mPlay.setOnClickListener(mPlay_listener);
        mPause.setOnClickListener(mPause_listener);
        mStop.setOnClickListener(mStop_listener);
        mVideoView.setSurfaceTextureListener(mVideoView_listener);
        mNative_test.setOnClickListener(mNativetest_listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initlayout();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}