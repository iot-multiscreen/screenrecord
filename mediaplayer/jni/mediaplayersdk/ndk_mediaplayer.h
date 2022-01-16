#ifndef NDK_MEDIAPLAYER_H_

#define NDK_MEDIAPLAYER_H_

#include "IMediaplayerBaseInterface.h"

using namespace android ;
typedef void (*stringarray_cb)(const char* eName, int numObjects, char strArray[][4096]);

struct NdkMediaPlayer : public IMediaplayerBaseInterface{

public:
    NdkMediaPlayer(stringarray_cb = NULL);
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
    virtual ~NdkMediaPlayer();

private:
    stringarray_cb mstringarray_cb;
};


#endif  // NDK_MEDIAPLAYER_H_
