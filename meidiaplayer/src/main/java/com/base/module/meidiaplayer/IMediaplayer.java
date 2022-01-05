package com.base.module.meidiaplayer;

import android.os.Handler;
import android.view.Surface;

public interface IMediaplayer {
    void init();
    void start();
    void pause();
    void stop();
    void release();
    int getDuration();
    int getCurrentPosition();
    void  setSurface(Surface sh);
    void setDatatSource(String path);
    void setEventHandler(Handler eventhandler);
}
