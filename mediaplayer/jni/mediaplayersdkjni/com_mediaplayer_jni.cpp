#define LOG_TAG "com_mediaplayer_jni"

#define MM_EVENT         "MMEvent"
#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <android/log.h>
#include <android/native_window_jni.h>
#include "utils/Log.h"
//using namespace android;
static void jni_stringarray_callback(const char* eName, int numObjects, 
                                        char strArray[][4096],const jobject& mediaplayerObj);

ANativeWindow *nativeWindow=NULL;

static JavaVM *gJavaVM = NULL;

#define FIND_CLASS(var, className) do{\
        var = env->FindClass(className); \
        var = jclass(env->NewGlobalRef(var));\
}while(0)
static struct {
    jclass mediaplayerClazz;
    jmethodID eventCallbackMethod;
} gMSSIClassInfo;

JNIEXPORT jint JNICALL nativeInit
  (JNIEnv * env, jobject mediaplayerObj,jint mtype)
{
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    return 0;
}

JNIEXPORT jint JNICALL nativePrepare
      (JNIEnv * env, jobject mediaplayerObj)
{
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    return 0;
}

JNIEXPORT jint JNICALL nativeStop
        (JNIEnv * env, jobject mediaplayerObj)
{
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    return 0;
}

JNIEXPORT jint JNICALL nativePlay
        (JNIEnv * env, jobject mediaplayerObj)
{
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    return 0;
}

JNIEXPORT jint JNICALL nativeSetVideoSurface
        (JNIEnv * env, jobject mediaplayerObj, jobject javaSurface)
{
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    nativeWindow =  ANativeWindow_fromSurface(env,javaSurface);
    if(NULL != nativeWindow ){

    }
    return 0;
}

JNIEXPORT jint JNICALL nativePause
        (JNIEnv * env, jobject mediaplayerObj)
{
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    return 0;
}

JNIEXPORT jint JNICALL nativeResume
        (JNIEnv * env, jobject mediaplayerObj)
{
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    return 0;
}

JNIEnv * getThreadEnv(bool *isAttached){
    JNIEnv *env;
    *isAttached = false;
    if(gJavaVM==NULL){
        return NULL;
    }
    int status = gJavaVM->GetEnv((void **) &env, JNI_VERSION_1_4);
    if (status < 0) {
        status = gJavaVM->AttachCurrentThread(&env, NULL);
        if (status < 0) {
            return NULL;
        }
        *isAttached = true;
    }
    return env;
}

void releaseThreadEnv(bool attach){
    if(attach){
        gJavaVM->DetachCurrentThread();
    }
}
/**
 * callback_handler is used as the callback function from .cpp to .java.
 * This function can be used in both Java threads and native threads.
 */
static void callback_handler(jstring eventName, jobjectArray oarr,
    jobject wfdSession)
{
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    int status;
    bool isAttached = false;
    JNIEnv *env=getThreadEnv(&isAttached);
    if(env==NULL){
        return;
    }else{
        do{
            /* Find the callback method ID */
            jmethodID method = env->GetMethodID(gMSSIClassInfo.mediaplayerClazz,
                    "eventCallback", "(Ljava/lang/String;[Ljava/lang/Object;)V");
            if (!method) {
                break;
            }
            const char* eName = env->GetStringUTFChars(eventName, NULL);
            env->ReleaseStringUTFChars(eventName, eName);
            env->CallVoidMethod(wfdSession, method, eventName, oarr);
        }while(0);
        releaseThreadEnv(isAttached);
    }
}

static JNINativeMethod gMethods[] = {
        // nameOfNativeMethod,
        // methodSignature,
        // methodPointer
        {   "nativeInit",
            "(I)I",
            (void*) nativeInit},
        {   "nativePrepare",
            "()I",
            (void*) nativePrepare},
        {   "nativeStop",
            "()I",
            (void*) nativeStop},
        {   "nativePlay",
            "()I",
            (void*) nativePlay},
        {   "nativePlay",
            "()I",
            (void*) nativePlay},
        {   "nativeSetVideoSurface",
            "(Landroid/view/Surface;)I",
            (void*) nativeSetVideoSurface},
        {   "nativePause",
                "()I",
                (void *)nativePause},
        {   "nativeResume",
                "()I",
                (void *)nativeResume},
};

/** =======================================================================
**               JNI OnLoad and OnUnload
** ======================================================================= */
JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved)
{
    JNIEnv *env;
    //store java virtual machine in global variable
    gJavaVM = vm;
    if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
        ALOGE("[%s][%d]\n",__FUNCTION__ ,__LINE__);
        return JNI_FALSE;
    }
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    /* Native function registration */
    FIND_CLASS(gMSSIClassInfo.mediaplayerClazz,
                "com/base/module/mediaplayer/MultiMediaEngine");
    if (env->RegisterNatives(gMSSIClassInfo.mediaplayerClazz,
        gMethods, sizeof(gMethods)/sizeof(gMethods[0])) < 0) {
        return JNI_FALSE;
    }

    gMSSIClassInfo.eventCallbackMethod = env->GetMethodID(
        gMSSIClassInfo.mediaplayerClazz, "eventCallback",
        "(Ljava/lang/String;[Ljava/lang/Object;)V");
    return JNI_VERSION_1_4;
}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved)
{
    gJavaVM = 0;
}

