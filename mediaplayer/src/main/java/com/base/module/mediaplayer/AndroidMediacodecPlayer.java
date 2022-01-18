package com.base.module.mediaplayer;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;

import com.base.module.mediaplayer.demux.AndroidDemux;
import com.base.module.mediaplayer.encoder.AndroidDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;

public class AndroidMediacodecPlayer extends BaseMediaplayer implements MediaplayerEngine.mediaCallBack{
    private final static String TAG = "player.AndroidMediacodecPlayer";
    private static AndroidMediacodecPlayer mediaPlayer;
    private Context mContext;
    private Surface mVideoSurface;
    private AndroidDemux mAndroidDemux;
    private MediaCodec mVideoCodec;
    private AndroidDecoder mAndroidDecoder;
    private MediaFormat mVideoFormat;
    private MediaFormat mAudioFormat;
    private int mVideoWidth,mVidewHeight,mFramerate;
    private Handler mEventHandler;
    private static final long TIMEOUT_US = 10000;
    private long mDuration;

    public static AndroidMediacodecPlayer getInstance(Context context){
        if(null == mediaPlayer){
            mediaPlayer = new AndroidMediacodecPlayer(context);
        }
        return mediaPlayer;
    }

    AndroidMediacodecPlayer(Context context){
        mAndroidDemux = AndroidDemux.getInstance();
        mAndroidDecoder = new AndroidDecoder();
        mContext = context;
    }

    public void setDatatSource(String path){
        mAndroidDemux.setDataSource(path);
        mAndroidDemux.getAVFormatInfo();
        mVideoFormat = mAndroidDemux.getVideoFormat();
        mAudioFormat = mAndroidDemux.getAudioFormat();
        mVideoWidth = mVideoFormat.getInteger(MediaFormat.KEY_WIDTH);
        mVidewHeight = mVideoFormat.getInteger(MediaFormat.KEY_HEIGHT);
        mFramerate = mVideoFormat.getInteger(MediaFormat.KEY_FRAME_RATE);
        Log.d(TAG,"mAudioFormat="+mAndroidDemux.getAudioFormat());
        Log.d(TAG,"mVideoFormat="+mAndroidDemux.getVideoFormat());
    }

    public void setEventHandler(Handler eventhandler){
        mEventHandler = eventhandler;
    }

    public void  setSurface(Surface sh){
        mVideoSurface = sh;
        Log.d(TAG,"setSurface");
    }

    public void mediaCodecConfigure(){
        Log.d(TAG,"mediaCodecConfigure");
        String mimetype = mVideoFormat.getString(MediaFormat.KEY_MIME);
        MediaFormat mediaFormat = MediaFormat.createVideoFormat(mimetype, mVideoWidth, mVidewHeight);
        mVideoCodec.configure(mediaFormat, mVideoSurface, null, 0);
        mEventHandler.obtainMessage(MediaplayerMSG.PLAYER_PREPARED).sendToTarget();
    }

    public void prepare(){

    }

    public void init(){
        if(mVideoCodec == null) {
            try {
                mVideoCodec = MediaCodec.createDecoderByType(mVideoFormat.getString(MediaFormat.KEY_MIME));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mediaCodecConfigure();
    }


    public void start() {
        Log.d(TAG,"start");
        mVideoCodec.start();

    }

    public void pause(){

    }

    public void stop(){

    }


    public void release(){

    }


    @Override
    public void CallBack(String eventName, Object[] objectArray) {

    }

}
