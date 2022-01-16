/************************************************************************************
*************************************************************************************
*************************************************************************************
*************************************************************************************
*************************************************************************************
************************************************************************************/

//#define LOG_NDEBUG 0
#define LOG_TAG "MediaplayerInterface"
#include <stdio.h>
#include "MediaplayerInterface.h"
#include "mediaplayersdk/ffmpeg_mediaplayer.h"
#include "mediaplayersdk/ndk_mediaplayer.h"
#include "utils/Log.h"
using namespace android;

MediaplayerInterface::MediaplayerInterface(stringarray_cb stringarray_cb)
    : mstringarray_cb(stringarray_cb),
      mMediaplayer(NULL){
    ALOGD("[%s][%d]this:%p",__FUNCTION__,__LINE__,this);
}

MediaplayerInterface::~MediaplayerInterface(){
    ALOGD("[%s][%d]this:%p",__FUNCTION__,__LINE__,this);
    stop();
}

int MediaplayerInterface::init(int type){
    int revalue = 0;
    if(NULL == mMediaplayer){
        switch (type) {
            case FFMPEG_PLAYER:
                mMediaplayer = new FFmpegMediaPlayer(mstringarray_cb);
                break;
            case NDK_PLAYER:
                mMediaplayer = new NdkMediaPlayer(mstringarray_cb);
                break;
            default:
                break;
        }
    };
    if(NULL != mMediaplayer) {
        mMediaplayer->init();
    }
    return revalue;
}

int MediaplayerInterface::setDatatSource(const char *url){
    int revalue = 0;
    return revalue;
}

int MediaplayerInterface::prepare(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    int restatus = -1;
    mMediaplayer->prepare();
    restatus = 0;
    return restatus;
}

int MediaplayerInterface::stop(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    int restatus = -1;
    mMediaplayer->stop();
    restatus = 0;
    return restatus;
}

int MediaplayerInterface::setSurfaceTex(ANativeWindow *nativewind){
    ALOGD( "[%s][%d][%p]\n",__FUNCTION__,__LINE__,nativewind);
    int restatus = -1;
    mMediaplayer->setSurfaceTex(nativewind);
    restatus = 0;
    return restatus;
}

int MediaplayerInterface::play(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    int restatus = -1;
    mMediaplayer->play();
    restatus = 0;
    return restatus;
}

int MediaplayerInterface::pause(){
        ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
        int restatus = -1;
        mMediaplayer->pause();
        restatus = 0;
        return restatus;
}


int MediaplayerInterface::resume(){
        ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
        int restatus = -1;
        mMediaplayer->resume();
        restatus = 0;
        return restatus;
}
