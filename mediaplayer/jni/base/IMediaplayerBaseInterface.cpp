/************************************************************************************
*************************************************************************************
*************************************************************************************
*************************************************************************************
*************************************************************************************
************************************************************************************/

//#define LOG_NDEBUG 0
#define LOG_TAG "IMediaplayerBaseInterface"

#include "utils/Log.h"
#include "IMediaplayerBaseInterface.h"

using namespace android;

IMediaplayerBaseInterface::IMediaplayerBaseInterface(stringarray_cb stringarray_cb)
    :mstringarray_cb(stringarray_cb){
        ALOGD("[%s][%d]this:%p",__FUNCTION__,__LINE__,this);
}

IMediaplayerBaseInterface::~IMediaplayerBaseInterface(){
        ALOGD("[%s][%d]this:%p",__FUNCTION__,__LINE__,this);
}

int IMediaplayerBaseInterface::init(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int IMediaplayerBaseInterface::setDatatSource(const char *url){
    ALOGD("[%s][%d]\n",__FUNCTION__,__LINE__);
    return 0;
}
int IMediaplayerBaseInterface::prepare(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int IMediaplayerBaseInterface::stop(){
   ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
   return 0;
}

int IMediaplayerBaseInterface::setSurfaceTex(ANativeWindow *nativewind){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
   return 0;
}

int IMediaplayerBaseInterface::play( ){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int IMediaplayerBaseInterface::pause(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int IMediaplayerBaseInterface::resume(){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
    return 0;
}

int IMediaplayerBaseInterface::eventCallbackState(const char* state){
    return 0;
}

