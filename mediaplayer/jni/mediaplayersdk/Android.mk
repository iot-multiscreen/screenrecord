LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := libmediaplayer_sdk
LOCAL_SRC_FILES :=  ffmpeg_mediaplayer.cpp \
                    ndk_mediaplayer.cpp

LOCAL_C_INCLUDES := $(LOCAL_PATH) \
                    $(LOCAL_PATH)/../ \
                    $(LOCAL_PATH)/../base/
LOCAL_STATIC_LIBRARIES := libmediaplayer_base
LOCAL_MODULE_TAGS := optional
LOCAL_LDLIBS := -llog
include $(BUILD_SHARED_LIBRARY)