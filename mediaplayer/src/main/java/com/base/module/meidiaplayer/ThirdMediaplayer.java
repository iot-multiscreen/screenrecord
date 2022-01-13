package com.base.module.meidiaplayer;

import android.content.Context;

public class ThirdMediaplayer extends BaseMediaplayer{
    private Context mContext;
    private ThirdMediaplayer mediaPlayer;

    public ThirdMediaplayer(Context context){
        if(null == mediaPlayer){
            mediaPlayer = new ThirdMediaplayer();
            mContext = context;
        }
    }

    public ThirdMediaplayer(){

    }
}
