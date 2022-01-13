package com.base.module.meidiaplayer;

import android.content.Context;
import android.media.MediaPlayer;

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
