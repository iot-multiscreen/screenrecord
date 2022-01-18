package com.base.module.mediaplayer.demux;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;

public class AndroidDemux {
    private final static String TAG = "player.AndroidDemux";
    private int video_index = 0;
    private int audio_index = 0;
    private MediaFormat mVideoFormat;
    private MediaFormat mAudioFormat;
    private MediaExtractor mediaExtractor;
    private static AndroidDemux mInstance = null;

    public static AndroidDemux getInstance(){
        if(mInstance == null){
            mInstance = new AndroidDemux();
            Log.d(TAG,"mInstance == null");
        }
        return mInstance;
    }

    AndroidDemux(){
        Log.d(TAG,"new AndroidDemux");
        mediaExtractor = new MediaExtractor();
    }

    public void setDataSource(String path){
        try {
            mediaExtractor.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAVFormatInfo() {
        int trackIndex = 0;
        int count = mediaExtractor.getTrackCount();//获取轨道数量
        for (int i = 0; i < count; i++) {
            MediaFormat mediaFormat = mediaExtractor.getTrackFormat(i);
            String currentTrack = mediaFormat.getString(MediaFormat.KEY_MIME);
            if (currentTrack.startsWith("video/")) {
                video_index = i;
                mVideoFormat = mediaFormat;
                continue;
            }
            if (currentTrack.startsWith("audio/")) {
                audio_index = i;
                mAudioFormat = mediaFormat;
                continue;
            }
        }
    }

    /*public String getLanguage(){
        String language = null;
            language = mVideoFormat.getString(MediaFormat.KEY_LANGUAGE);
        return language;
    }

    public int getVideoWidth(){
        int width = 0;
        width = mVideoFormat.getInteger(MediaFormat.KEY_WIDTH);
        return width;
    }

    public int getVideoHeight(){
        int height = 0;
        height = mVideoFormat.getInteger(MediaFormat.KEY_HEIGHT);
        return height;
    }

    public long getDuration(){
        long durationTime = 0;
        durationTime = mVideoFormat.getInteger(MediaFormat.KEY_DURATION);
        return durationTime;
    }

    public int getSampleRate(){
        int sampleRate = 0;
        sampleRate = mAudioFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE);
        return sampleRate;
    }

    public int getChannelCount (){
        int channelCount  = 0;
        channelCount  = mAudioFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
        return channelCount ;
    }

    public int getMaxWidth (){
        int maxWidth   = 0;
        maxWidth   = mVideoFormat.getInteger(MediaFormat.KEY_MAX_WIDTH);
        return maxWidth  ;
    }

    public int getMaxHeight  (){
        int maxHeight    = 0;
        maxHeight = mVideoFormat.getInteger(MediaFormat.KEY_MAX_HEIGHT);
        return maxHeight   ;
    }

    public int getBitRate(){
        int bitRate  = 0;
        bitRate  = mVideoFormat.getInteger(MediaFormat.KEY_BIT_RATE);
        return bitRate ;
    }

    public int getColorFormat(){
        int colorFormat = 0;
        colorFormat = mVideoFormat.getInteger(MediaFormat.KEY_COLOR_FORMAT);
        return colorFormat;
    }

    public int getFrameRate (){
        int frameRate  = 0;
        frameRate = mVideoFormat.getInteger(MediaFormat.KEY_FRAME_RATE);
        return frameRate ;
    }

    public int getGridRows(){
        int gridRows = 0;
        gridRows = mVideoFormat.getInteger(MediaFormat.KEY_GRID_ROWS);
        return gridRows;
    }

    public int getGridColumns(){
        int gridColumns = 0;
        gridColumns = mVideoFormat.getInteger(MediaFormat.KEY_GRID_COLUMNS);
        return gridColumns;
    }

    public int getPcmEncoding(){
        int pcmEncoding = 0;
        pcmEncoding = mVideoFormat.getInteger(MediaFormat.KEY_PCM_ENCODING);
        return pcmEncoding;
    }

    public float getCaptureRate(){
        float captureRate = 0;
        captureRate = mVideoFormat.getFloat(MediaFormat.KEY_CAPTURE_RATE);
        return captureRate;

    }

    public int getisAdts(){
        int isAdts = 0;
        isAdts = mVideoFormat.getInteger(MediaFormat.KEY_IS_ADTS);
        return isAdts;
    }

    public String getMimeType(){
        String mimetype ;
        mimetype = mVideoFormat.getString(MediaFormat.KEY_MIME);
        return mimetype;
    }*/

    public MediaFormat getVideoFormat(){
        return mVideoFormat;
    }

    public MediaFormat getAudioFormat(){
        return mAudioFormat;
    }

    public boolean decodeMediaData(MediaCodec decoder, ByteBuffer[] inputBuffers) {
        boolean isMediaEOS = false;
        int inputBufferIndex = decoder.dequeueInputBuffer(10000);
        Log.d(TAG,"inputBufferIndex ="+inputBufferIndex);
        if (inputBufferIndex >= 0) {
            ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
            int sampleSize = mediaExtractor.readSampleData(inputBuffer, 0);
            if (sampleSize < 0) {
                decoder.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                isMediaEOS = true;
                Log.d(TAG, "end of stream");
            } else {
                decoder.queueInputBuffer(inputBufferIndex, 0, sampleSize, mediaExtractor.getSampleTime(), 0);
                mediaExtractor.advance();
            }
        }
        return isMediaEOS;
    }
}
