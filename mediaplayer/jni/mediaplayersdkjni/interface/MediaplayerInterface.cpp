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
#include "utils/Log.h"
using namespace android;

MediaplayerInterface::MediaplayerInterface(stringarray_cb stringarray_cb)
    : mstringarray_cb(stringarray_cb){
    ALOGD("[%s][%d]this:%p",__FUNCTION__,__LINE__,this);
}

MediaplayerInterface::~MediaplayerInterface(){
    ALOGD("[%s][%d]this:%p",__FUNCTION__,__LINE__,this);
    stop();
}

int MediaplayerInterface::init(int type){
    int revalue = 0;
    if(NULL == mMediaplayer){
        ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    }
    if(NULL != mMediaplayer){
        mMediaplayer->init(type);
    }

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
