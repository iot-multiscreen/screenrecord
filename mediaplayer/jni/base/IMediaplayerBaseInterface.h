#ifndef _MEDIAPLAYER_BASE_INTERFACE_
#define _MEDIAPLAYER_BASE_INTERFACE_

#include <android/native_window_jni.h>

namespace android{
typedef void (*stringarray_cb)(const char* eName, int numObjects, char strArray[][4096]);

//abstract class , InterFace
class IMediaplayerBaseInterface {
public:
    IMediaplayerBaseInterface(stringarray_cb = NULL);
    ~IMediaplayerBaseInterface();
    virtual int init();
    virtual int setDatatSource(const char *url);
    virtual int prepare();
    virtual int stop();
    virtual int play();
    virtual int setSurfaceTex(ANativeWindow *nativewind);
    virtual int pause();
    virtual int resume();
    virtual int eventCallbackState(const char* state);
protected:
    stringarray_cb mstringarray_cb;
};
}
#endif
