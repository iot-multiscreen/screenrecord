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

namespace android{

    IMediaplayerBaseInterface::IMediaplayerBaseInterface(stringarray_cb stringarray_cb)
    :mstringarray_cb(stringarray_cb),
    mSessionId(0){
        ALOGD("[%s][%d]this:%p",__FUNCTION__,__LINE__,this);
}

    IMediaplayerBaseInterface::~IMediaplayerBaseInterface(){
        ALOGD("[%s][%d]this:%p",__FUNCTION__,__LINE__,this);
}

int IMediaplayerBaseInterface::init(int type){
    ALOGD("[%s][%d]",__FUNCTION__,__LINE__);
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

}
