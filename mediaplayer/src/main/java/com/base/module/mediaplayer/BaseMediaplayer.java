package com.base.module.mediaplayer;

import android.os.Handler;
import android.util.Log;
import android.view.Surface;

public class BaseMediaplayer implements IMediaplayer{
    private BaseMediaplayer mediaplayer;
    public BaseMediaplayer getInstance(){
        /*if(mediaplayer != null){
            mediaplayer = new BaseMediaplayer();
        }*/
        return mediaplayer;
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void release() {

    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void setSurface(Surface sh) {

    }

    @Override
    public void setDatatSource(String path) {

    }
    @Override
    public void setEventHandler(Handler eventhandler){

    }


    }
