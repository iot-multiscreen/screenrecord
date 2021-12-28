package com.base.module.h264player;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MediacodecThread  extends Thread  {
        private final static String TAG = "h264player.MediacodecThread";
        //解码器
        private MediaCodecUtil util;
        private boolean isFinish = false;
        private final static int TIME_INTERNAL = 30;
        private final static int HEAD_OFFSET = 512;
        private Handler mHandler;
        private File h264File ;
        /**
         * 初始化解码器
         *
         * @param util 解码Util
         * @param path 文件路径
         */
        public MediacodecThread(MediaCodecUtil util, String path) {
            this.util = util;
            h264File = new File(path);
        }

        public void setEventHandler(Handler eventhandler){
            mHandler = eventhandler;
        }
    /**
     * Find H264 frame head
     *
     * @param buffer
     * @param len
     * @return the offset of frame head, return 0 if can not find one
     */
    private int findHead(byte[] buffer, int len) {
        int i;
        for (i = HEAD_OFFSET; i < len; i++) {
            if (checkHead(buffer, i))
                break;
        }
        if (i == len)
            return 0;
        if (i == HEAD_OFFSET)
            return 0;
        return i;
    }

    /**
     * Check if is H264 frame head
     *
     * @param buffer
     * @param offset
     * @return whether the src buffer is frame head
     */
    private boolean checkHead(byte[] buffer, int offset) {
        // 00 00 00 01
        if (buffer[offset] == 0 && buffer[offset + 1] == 0
                && buffer[offset + 2] == 0 && buffer[3] == 1)
            return true;
        // 00 00 01
        if (buffer[offset] == 0 && buffer[offset + 1] == 0
                && buffer[offset + 2] == 1)
            return true;
        return false;
    }
        /**
         * I帧或者P帧
         */
        private boolean isVideoFrameHeadType(byte head) {
            Log.d(TAG,"isVideoFrameHeadType");
            return head == (byte) 0x65 || head == (byte) 0x61 || head == (byte) 0x41;
        }

        @Override
        public void run() {
                int h264Read = 0;
                int frameOffset = 0;
                byte[] buffer = new byte[100000];
                byte[] framebuffer = new byte[200000];
                boolean readFlag = true;
                InputStream is = null;
                FileInputStream fs = null;
                try {
                    fs = new FileInputStream(h264File);
                    is = new BufferedInputStream(fs);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                while (!Thread.interrupted() && readFlag && !isFinish) {
                    try {
                        int length = is.available();
                        if (length > 0) {
                            // Read file and fill buffer
                            int count = is.read(buffer);
                            Log.i(TAG, "" + count);
                            h264Read += count;
                            Log.d(TAG, "count:" + count + " h264Read:"
                                    + h264Read);
                            // Fill frameBuffer
                            if (frameOffset + count < 200000) {
                                System.arraycopy(buffer, 0, framebuffer,
                                        frameOffset, count);
                                frameOffset += count;
                            } else {
                                frameOffset = 0;
                                System.arraycopy(buffer, 0, framebuffer,
                                        frameOffset, count);
                                frameOffset += count;
                            }

                            // Find H264 head
                            int offset = findHead(framebuffer, frameOffset);
                            Log.i(TAG, " Head:" + offset);
                            while (offset > 0) {
                                if (checkHead(framebuffer, 0)) {
                                    // Fill decoder
                                    boolean flag = util.onFrame(framebuffer, 0, offset);
                                    if (flag) {
                                        byte[] temp = framebuffer;
                                        framebuffer = new byte[200000];
                                        System.arraycopy(temp, offset, framebuffer,
                                                0, frameOffset - offset);
                                        frameOffset -= offset;
                                        Log.e(TAG, "is Head:" + offset);
                                        // Continue finding head
                                        offset = findHead(framebuffer, frameOffset);
                                    }
                                } else {
                                    offset = 0;
                                }

                            }
                            Log.d(TAG, "end loop");
                        } else {
                            h264Read = 0;
                            frameOffset = 0;
                            readFlag = false;
                            sendMessage(MediaCodecUtil.PLAYSTOP);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(TIME_INTERNAL);
                    } catch (InterruptedException e) {

                    }
                }
            };

        //手动终止读取文件，结束线程
        public void stopThread() {
            isFinish = true;
        }

    private void sendMessage(int messagetype) {
        if (mHandler != null) {
            mHandler.obtainMessage(messagetype).sendToTarget();
        }
    }
}
