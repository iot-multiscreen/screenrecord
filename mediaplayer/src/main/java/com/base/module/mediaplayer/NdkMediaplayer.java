package com.base.module.mediaplayer;

import android.content.Context;

public class NdkMediaplayer extends BaseMediaplayer{
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
}
