
//#define LOG_NDEBUG 0
#define LOG_TAG "ffmpeg_mediaplayer"

#include "ffmpeg_mediaplayer.h"
#include "utils/Log.h"
using namespace android;

FFmpegMediaPlayer::FFmpegMediaPlayer(stringarray_cb stringarray_cb)
        :mstringarray_cb(stringarray_cb){
    ALOGD("[%s][%d]this:%p",__FUNCTION__,__LINE__,this);
}

FFmpegMediaPlayer::~FFmpegMediaPlayer(){
    ALOGD("[%s][%d]this:%p",__FUNCTION__,__LINE__,this);
}

int FFmpegMediaPlayer::init(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int FFmpegMediaPlayer::setDatatSource(const char *url){
    ALOGD("[%s][%d]\n",__FUNCTION__,__LINE__);
    return 0;
}

int FFmpegMediaPlayer::prepare(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int FFmpegMediaPlayer::stop(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int FFmpegMediaPlayer::setSurfaceTex(ANativeWindow *nativewind){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int FFmpegMediaPlayer::play( ){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int FFmpegMediaPlayer::pause(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int FFmpegMediaPlayer::resume(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int FFmpegMediaPlayer::eventCallbackState(const char* state){
    return 0;
}
