package com.base.module.mediaplayer;

import android.content.Context;

public class NdkMediaplayer extends BaseMediaplayer implements MediaplayerEngine.mediaCallBack{
    private Context mContext;
    private NdkMediaplayer mediaPlayer;

    public NdkMediaplayer(Context context){
        if(null == mediaPlayer){
            mediaPlayer = new NdkMediaplayer();
            mContext = context;
        }
    }

    public NdkMediaplayer(){

    }

    @Override
    public void CallBack(String eventName, Object[] objectArray) {

    }
}
