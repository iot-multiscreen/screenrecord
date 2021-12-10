package com.base.module.screenrecord;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class RecordService extends Service {
    private final static String TAG = "ScreenRecord.RecordService";
    private MediaCodec mMediaCodec;
    private MediaFormat mMediaFormat;
    private int mScreen_width,mScreen_height,mScreen_orientation,mScreen_DPI;
    private DisplayManager mDisplayManager;
    private Surface mSurface;
    private EncordThread mEncordThread;
    private final static String VIRTUALDEVICE = "screenrecord";
    public RecordService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");
        try {
            createEncoderMediacodec();
        } catch (IOException e) {
            e.printStackTrace();
        }
        createVirtualDisplay();
        mEncordThread = new EncordThread();
        mEncordThread.run();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public ComponentName startService(Intent service) {
        Log.d(TAG,"startService");
        return super.startService(service);
    }

    /*create the encoder mediacodec
       mediacodec createInputSurface usefor Binding virtualdisplay
    * */
    private void createEncoderMediacodec() throws IOException {
        Log.d(TAG,"createEncoder");
        getScreenRes();
        initMediaFormat();
        mSurface = mMediaCodec.createInputSurface();
        mMediaCodec.start();
    }

    //get current screen size
    private void getScreenRes(){
        Log.d(TAG,"getScreenRes");
        Display mDisplay = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        Point size = new Point();
        mDisplay.getRealSize(size);
        Configuration config = getResources().getConfiguration();
        mScreen_width = size.x;
        mScreen_height = size.y;
        mScreen_orientation = config.orientation;
        mScreen_DPI = Math.min(mScreen_width,mScreen_height)* DisplayMetrics.DENSITY_XHIGH / 1080;
    }

    // init the media encoder media format and configure the mediacodec
    private void initMediaFormat() throws IOException {
        Log.d(TAG,"initMediaFormat mScreen_width="+mScreen_width+" mScreen_height="+mScreen_height);
        mMediaFormat = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC,mScreen_width,mScreen_height);
        mMediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT,MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        mMediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE,60);
        mMediaFormat.setInteger(MediaFormat.KEY_BIT_RATE,4*1024*1024);
        mMediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL,1);
        mMediaFormat.setInteger(MediaFormat.KEY_PROFILE, MediaCodecInfo.CodecProfileLevel.AVCProfileBaseline);
        mMediaFormat.setInteger(MediaFormat.KEY_LEVEL,MediaCodecInfo.CodecProfileLevel.AVCLevel3);
        mMediaFormat.setInteger(MediaFormat.KEY_WIDTH,mScreen_width);
        mMediaFormat.setInteger(MediaFormat.KEY_HEIGHT,mScreen_height);
        mMediaFormat.setInteger(MediaFormat.KEY_REPEAT_PREVIOUS_FRAME_AFTER,5000);
        mMediaFormat.setInteger(MediaFormat.KEY_PREPEND_HEADER_TO_SYNC_FRAMES,1);
        mMediaCodec = MediaCodec.createEncoderByType("video/avc");
        mMediaCodec.configure(mMediaFormat,null,null, MediaCodec.CONFIGURE_FLAG_ENCODE);
    }

    // create the virtualdisplay neet system.uid permission
    private void createVirtualDisplay(){
        if (null == mDisplayManager){
            mDisplayManager = (DisplayManager)getSystemService(Context.DISPLAY_SERVICE);
        }
        int flags = 0;
        flags |= DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC
                | DisplayManager.VIRTUAL_DISPLAY_FLAG_PRESENTATION;
        mDisplayManager.createVirtualDisplay(VIRTUALDEVICE, mScreen_width, mScreen_height, mScreen_DPI, mSurface, flags);
    }

    //for save the encode data
    private void saveEncorderData(ByteBuffer bfile, String filePath,String fileName) throws IOException {
        File file=new File(filePath + "/" +fileName);
        if(!file.exists()){
            Log.d(TAG,"file.exists() pre createNewFile");
            file.createNewFile();
        }
        byte[] bytesStream = new byte[bfile.remaining()];
        FileOutputStream fe=new FileOutputStream(file,true);
        bfile.get(bytesStream, 0, bytesStream.length);
        fe.write(bytesStream);
        fe.flush();
        fe.close();
    }

    //add the thread for encord
    private class EncordThread extends Thread{
        @Override
        public void run() {
            super.run();
            MediaCodec.BufferInfo aBufferInfo = new MediaCodec.BufferInfo();
            int outindex = mMediaCodec.dequeueOutputBuffer(aBufferInfo,2*1000);
            while (outindex >= 0){
                ByteBuffer outputBuffer = mMediaCodec.getOutputBuffer(outindex);
                outputBuffer.position(aBufferInfo.offset);
                outputBuffer.limit(aBufferInfo.offset + aBufferInfo.size);
                try {
                    saveEncorderData(outputBuffer,"/storage/emulated/0/Download","screenrecord.h264");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                outputBuffer.clear();
                mMediaCodec.releaseOutputBuffer(outindex,false);
                Log.d(TAG,"outindex="+outindex);
            }
        }
    }
}