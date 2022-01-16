#define LOG_TAG "com_mediaplayer_jni"

#define MP_EVENT         "MPEvent"
#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <android/log.h>
#include <android/native_window_jni.h>
#include "utils/Log.h"
#include "interface/MediaplayerInterface.h"

using namespace android;
static void jni_stringarray_callback(const char* eName, int numObjects, 
                                        char strArray[][4096],const jobject& mediaplayerObj);
static void mediaplayer_stringarray_callback(const char* eName, int numObjects,
                                             char strArray[][4096]);

ANativeWindow *nativeWindow=NULL;
MediaplayerInterface *mediaplayerInterface = NULL;
jobject mMediaplayerObj = NULL;
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
    if(mMediaplayerObj){
        ALOGD("[%s][%d] Maybe mMediaplayerObj(%p)didn't close last time \n",
              __FUNCTION__,__LINE__,mMediaplayerObj);
        env->DeleteGlobalRef(mMediaplayerObj);
    }
    mMediaplayerObj = env->NewGlobalRef(mediaplayerObj);
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    if(NULL == mediaplayerInterface){
        mediaplayerInterface = new MediaplayerInterface(mediaplayer_stringarray_callback);
    }
    mediaplayerInterface->init(mtype);
    return 0;
}

JNIEXPORT jint JNICALL nativeSetDatatSource
        (JNIEnv * env, jobject mediaplayerObj,jstring url)
{
    const char *murl = env->GetStringUTFChars(url, NULL);
    if(NULL != mediaplayerInterface){
        mediaplayerInterface->setDatatSource(murl);
    }
    env->ReleaseStringUTFChars(url, murl);
    return 0;
}

JNIEXPORT jint JNICALL nativePrepare
      (JNIEnv * env, jobject mediaplayerObj)
{
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    mediaplayerInterface->prepare();
    return 0;
}

JNIEXPORT jint JNICALL nativeStop
        (JNIEnv * env, jobject mediaplayerObj)
{

    env->DeleteGlobalRef(mMediaplayerObj);
    mMediaplayerObj = NULL;
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    return 0;
}

JNIEXPORT jint JNICALL nativePlay
        (JNIEnv * env, jobject mediaplayerObj)
{
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    mediaplayerInterface->play();
    return 0;
}

JNIEXPORT jint JNICALL nativeSetVideoSurface
        (JNIEnv * env, jobject mediaplayerObj, jobject javaSurface)
{
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    nativeWindow =  ANativeWindow_fromSurface(env,javaSurface);
    if(NULL != nativeWindow ){
        mediaplayerInterface->setSurfaceTex(nativeWindow);
    }
    return 0;
}

JNIEXPORT jint JNICALL nativePause
        (JNIEnv * env, jobject mediaplayerObj)
{
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    mediaplayerInterface->pause();
    return 0;
}

JNIEXPORT jint JNICALL nativeResume
        (JNIEnv * env, jobject mediaplayerObj)
{
    ALOGD("[%s][%d]\n",__FUNCTION__ ,__LINE__);
    mediaplayerInterface->resume();
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
    jobject mediaplayerObj)
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
            env->CallVoidMethod(mediaplayerObj, method, eventName, oarr);
        }while(0);
        releaseThreadEnv(isAttached);
    }
}


static void mediaplayer_stringarray_callback(const char* eName, int numObjects,
                                             char strArray[][4096]){
    ALOGD("stringarray_callback  eName=%s  numObjects=%d", eName, numObjects);
    if(nativeWindow != NULL){
        bool isAttached = false;
        JNIEnv *env=getThreadEnv(&isAttached);
        if(env==NULL){
            ALOGE("[%s][%d] getThreadEnv erro!!!!",__FUNCTION__,__LINE__);
            return;
        }else{
            do{
                if(mMediaplayerObj){
                    ALOGD("[%s][%d]\n",__FUNCTION__,__LINE__);
                    jni_stringarray_callback(eName, numObjects, strArray,mMediaplayerObj);
                }
            }while(0);
        }
    }else{
        if(mMediaplayerObj){
            jni_stringarray_callback(eName, numObjects, strArray,mMediaplayerObj);
        }
    }
}

static void jni_stringarray_callback(const char* eName, int numObjects,
                                     char strArray[][4096],const jobject& mediaplayerObj)
{
    ALOGD("stringarray_callback  eName=%s  numObjects=%d", eName, numObjects);
    if(numObjects >= 4 && !strcmp(eName,MP_EVENT)) {
        if(!strcmp(strArray[0],"StreamStarted")) {
            ALOGD("Received StreamStarted");
        }
    }
//    for (int i=0; i<numObjects; i++) {
//        ALOGD("\t strArray[%d] = \"%s\"", i, strArray[i]);
//    }

    bool isAttached = false;
    JNIEnv *env=getThreadEnv(&isAttached);
    if(env==NULL){
        ALOGE("[%s][%d] getThreadEnv erro!!!!",__FUNCTION__,__LINE__);
        return;
    }else{
        do {
            jstring eventName = env->NewStringUTF(eName);
            jclass objArrCls = env->FindClass("java/lang/Object");
            jobjectArray oarr = env->NewObjectArray(numObjects, objArrCls, NULL);
            jbyteArray strArr[numObjects];
            for (int i = 0; i < numObjects; i++) {
                strArr[i] = env->NewByteArray(4096);
                env->SetByteArrayRegion(strArr[i], 0, 4096,
                                        reinterpret_cast<const jbyte *>(strArray[i]));
                env->SetObjectArrayElement(oarr, i, strArr[i]);
                env->DeleteLocalRef(strArr[i]);
            }
            callback_handler(eventName, oarr, mediaplayerObj);
            env->DeleteLocalRef(eventName);
            env->DeleteLocalRef(oarr);
            env->DeleteLocalRef(objArrCls);
        }while (0);
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
        {   "nativeSetDatatSource",
            "(Ljava/lang/String;)I",
             (void*) nativeSetDatatSource},
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
                "com/base/module/mediaplayer/MediaplayerEngine");
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

