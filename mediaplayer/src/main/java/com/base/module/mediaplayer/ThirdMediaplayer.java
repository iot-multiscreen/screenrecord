package com.base.module.mediaplayer;

import android.content.Context;

public class ThirdMediaplayer extends BaseMediaplayer implements MediaplayerEngine.mediaCallBack{
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

    @Override
    public void CallBack(String eventName, Object[] objectArray) {

    }
}
