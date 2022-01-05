package com.base.module.meidiaplayer;

import android.content.Context;
import android.os.Handler;
import android.view.Surface;

public class MeidaplayerTest extends BaseMediaplayer{
    private final static String TAG = "player.Meidaplayer";
    private BaseMediaplayer mediaplayer ;
    private Handler mHandler;
    public final static int ANDROID_PLAYER = 0;
    public final static int NDK_PLAYER = 1;
    public final static int THIRD_SDK_PLAYER = 2;
    public MeidaplayerTest(int mediatype, Context context){
        if(null == mediaplayer){
            switch (mediatype){
                case ANDROID_PLAYER:
                    mediaplayer = new AndroidMediaplayer(context);
                    break;
                case NDK_PLAYER:
                    mediaplayer = new NdkMediaplayer(context);
                    break;
                case THIRD_SDK_PLAYER:
                    mediaplayer = new ThirdMediaplayer(context);
                    break;
                default:
                    break;
            }
        }
    }

    public void setEventHandler(Handler eventhandler){
        mediaplayer.setEventHandler(eventhandler);
    }

    @Override
    public void init() {
        mediaplayer.init();
    }

    @Override
    public void start() {
        mediaplayer.start();
    }

    @Override
    public void pause() {
        mediaplayer.pause();
    }

    @Override
    public void stop() {
        mediaplayer.stop();
    }

    @Override
    public void release(){
        mediaplayer.release();
    }

    @Override
    public int getDuration() {
        int duration = 0;
        duration = mediaplayer.getDuration();
        return duration;
    }

    @Override
    public int getCurrentPosition() {
        int currentposition = 0;
        currentposition = mediaplayer.getCurrentPosition();
        return currentposition;
    }

    @Override
    public void setSurface(Surface sh) {
        mediaplayer.setSurface(sh);
    }

    @Override
    public void setDatatSource(String path) {
        mediaplayer.setDatatSource(path);
    }
}
