//#define LOG_NDEBUG 0
#define LOG_TAG "ndk_mediaplayer"
#include "utils/Log.h"
#include "ndk_mediaplayer.h"
//using namespace android;

NdkMediaPlayer::NdkMediaPlayer(stringarray_cb stringarray_cb)
        :mstringarray_cb(stringarray_cb){
    ALOGD("[%s][%d]this:%p",__FUNCTION__,__LINE__,this);
}

NdkMediaPlayer::~NdkMediaPlayer(){
    ALOGD("[%s][%d]this:%p",__FUNCTION__,__LINE__,this);
}

int NdkMediaPlayer::init(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int NdkMediaPlayer::setDatatSource(const char *url){
    ALOGD("[%s][%d]\n",__FUNCTION__,__LINE__);
    return 0;
}

int NdkMediaPlayer::prepare(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int NdkMediaPlayer::stop(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int NdkMediaPlayer::setSurfaceTex(ANativeWindow *nativewind){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int NdkMediaPlayer::play( ){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int NdkMediaPlayer::pause(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int NdkMediaPlayer::resume(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int NdkMediaPlayer::eventCallbackState(const char* state){
    return 0;
}


