#ifndef _H_UTILS_LOG_H_
#define _H_UTILS_LOG_H_

#include<android/log.h>

#ifndef LOG_TAG
#define LOG_TAG    __FILE__
#endif

#ifndef LOG_ALWAYS_FATAL_IF
#define CONDITION(cond)     (__builtin_expect((cond)!=0, 0))
#define LOG_ALWAYS_FATAL_IF(cond,...) \
     ( (CONDITION(cond)) \
     ? ((void)__android_log_assert(#cond, LOG_TAG, ## __VA_ARGS__)) \
     : (void)0 )

    #ifndef LOG_ALWAYS_FATAL
    #define LOG_ALWAYS_FATAL(...) \
    ( ((void)__android_log_assert(NULL, LOG_TAG, ## __VA_ARGS__)) )
    #endif
#endif

#ifndef LOG_FATAL_IF
#define CONDITION(cond)     (__builtin_expect((cond)!=0, 0))
#define LOG_FATAL_IF(cond,...) \
     ( (CONDITION(cond)) \
     ? ((void)__android_log_assert(#cond, LOG_TAG, ## __VA_ARGS__)) \
     : (void)0 )
#endif

    #define ALOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
    #define ALOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__) 
    #define ALOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__) 
    #define ALOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
    #define ALOGF(...)  __android_log_print(ANDROID_LOG_FATAL,LOG_TAG,__VA_ARGS__)
    #define ALOGV(...)  __android_log_print(ANDROID_LOG_VERBOSE,LOG_TAG,__VA_ARGS__)
    #define ALOG_ASSERT(cond, ...) LOG_ALWAYS_FATAL_IF(!(cond), ##__VA_ARGS__)
#endif