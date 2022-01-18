package com.base.module.mediaplayer.encoder;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.view.Surface;

import com.base.module.mediaplayer.demux.AndroidDemux;

import java.io.IOException;

public class AndroidDecoder {
    private final static String TAG = "AndroidDecoder";
    private static AndroidDecoder mInstance = null;


    public AndroidDecoder getInstance(){
        if(mInstance == null){
            mInstance = new AndroidDecoder();
        }
        return mInstance;
    }

    public void createDecoderByType(String mtype){
        try {
            MediaCodec.createDecoderByType(mtype);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
