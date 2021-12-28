package com.base.module.h264player;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.nio.ByteBuffer;

public class MediaCodecUtil {
    private String TAG = "h264player.MediaCodecUtil";
    public final static int PLAYSTOP = 0;
    private SurfaceHolder holder;
    private int width, height;
    private MediaCodec mCodec;
    private boolean isFirst = true;
    private final static int TIME_INTERNAL = 5;

    public MediaCodecUtil(SurfaceHolder holder, int width, int height) {
        Log.d(TAG,"MediaCodecUtil() called with: " + "holder = [" + holder + "], " +
                "width = [" + width + "], height = [" + height + "]");
        this.holder = holder;
        this.width = width;
        this.height = height;
    }

    public MediaCodecUtil(SurfaceHolder holder) {
        this(holder, holder.getSurfaceFrame().width(), holder.getSurfaceFrame().height());
    }

    public void startCodec(String mimetype) {
        Log.d(TAG,"startCodec");
        if (isFirst) {
            initDecoder(mimetype);
        }
    }

    private void initDecoder(String mimetype) {
        Log.d(TAG,"initDecoder");
        try {
            mCodec = MediaCodec.createDecoderByType(mimetype);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MediaFormat mediaFormat = MediaFormat.createVideoFormat(mimetype, width, height);
        mCodec.configure(mediaFormat, holder.getSurface(), null, 0);
        mCodec.start();
        isFirst = false;
    }

    //int mCount = 0;
    public boolean onFrame(byte[] buf, int offset, int length) {
        Log.e(TAG, "onFrame start");
        ByteBuffer[] inputBuffers = mCodec.getInputBuffers();
        int inputBufferIndex = mCodec.dequeueInputBuffer(100);

        Log.e(TAG, "onFrame index:" + inputBufferIndex);
        if (inputBufferIndex >= 0) {
            ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
            inputBuffer.clear();
            inputBuffer.put(buf, offset, length);
            mCodec.queueInputBuffer(inputBufferIndex, 0, length, 0, 0);
            //mCount++;
        } else {
            return false;
        }

        // Get output buffer index
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int outputBufferIndex = mCodec.dequeueOutputBuffer(bufferInfo, 100);
        while (outputBufferIndex >= 0) {
            mCodec.releaseOutputBuffer(outputBufferIndex, true);
            outputBufferIndex = mCodec.dequeueOutputBuffer(bufferInfo, 0);
        }
        Log.e(TAG, "onFrame end");
        return true;
    }

    public void stopCodec() {
        try {
            mCodec.stop();
            mCodec.release();
            mCodec = null;
            isFirst = true;
        } catch (Exception e) {
            e.printStackTrace();
            mCodec = null;
        }
    }
}
