#ifndef _MEDIAPLAER_INTERFACE_H
#define _MEDIAPLAER_INTERFACE_H
#include <android/native_window_jni.h>
#include "base/IMediaplayerBaseInterface.h"

using namespace android;

typedef void (*stringarray_cb)(const char* eName, int numObjects, char strArray[][4096]);

class MediaplayerInterface {
public:
    MediaplayerInterface(stringarray_cb = NULL);
    ~MediaplayerInterface();

    int init(int type);
    int setDatatSource(const char *url);
    int prepare();
    int stop();
    int play();
    int setSurfaceTex(ANativeWindow *nativewind);
    int pause();
    int resume();
private:
    stringarray_cb mstringarray_cb;
    IMediaplayerBaseInterface *mMediaplayer;
    enum {
        FFMPEG_PLAYER,
        NDK_PLAYER,
    };
};

#endif
