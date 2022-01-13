package com.base.module.meidiaplayer;

import android.view.Surface;

public class MediaplayerEngine {
    static {
        System.loadLibrary("mediaplayer_native");
    }

    private mediaCallBack mCallBack;

    public interface mediaCallBack {
        void CallBack(String eventName, Object[] objectArray);
    }
    public void addMediaCallBack(mediaCallBack callBack) {
        this.mCallBack = callBack;
    }
    public native int nativeInit(int type);
    public native int nativePrepare();
    public native int nativeStop();
    public native int nativePlay();
    public native int nativeSetVideoSurface(Surface surface);
    public native int nativePause();
    public native int nativeResume();
    private void eventCallback(String eventName, Object[] objectArray) {
        if (mCallBack == null) {
            return;
        }
        mCallBack.CallBack(eventName, objectArray);
    }
}
