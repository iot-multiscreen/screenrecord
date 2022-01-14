package com.base.module.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Surface;

import java.io.IOException;

public class AndroidMediaplayer extends BaseMediaplayer{
    private final static String TAG = "player.AndroidMediaplayer";
    private Context mContext;
    private MediaPlayer mediaPlayer;
    private Handler mEventHandler;
    public AndroidMediaplayer(Context context){
        if(null == mediaPlayer){
            mediaPlayer = new MediaPlayer();
            mContext = context;
        }
    }

    public void setEventHandler(Handler eventhandler){
        mEventHandler = eventhandler;
    }

    public void setDatatSource(String path){
        try {
            mediaPlayer.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  setSurface(Surface sh){
        mediaPlayer.setSurface(sh);
    }

    public void prepare(){
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        mediaPlayer.prepareAsync();
        setOnPreparedListener();
        setOnVideoSizeChangedListener();
    }


    public void start(){
        mediaPlayer.start();
    }

    public void pause(){
        mediaPlayer.pause();
    }

    public void stop(){
        mediaPlayer.stop();

    }
    public int getDuration(){
       return  mediaPlayer.getDuration();
    }

    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    public void release(){
        mediaPlayer.release();
    }

    public void setOnPreparedListener(){
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mEventHandler.obtainMessage(MediaplayerMSG.PLAYER_PREPARED).sendToTarget();
            }
        });
    }

    public void setOnVideoSizeChangedListener(){
        mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
                mEventHandler.obtainMessage(MediaplayerMSG.VIDEOSIZECHANGED).sendToTarget();
            }
        });
    }
}
