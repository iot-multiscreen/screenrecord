LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := libmediaplayer_base
LOCAL_SRC_FILES :=  IMediaplayerBaseInterface.cpp

LOCAL_C_INCLUDES := $(LOCAL_PATH) \
                    $(LOCAL_PATH)/../
LOCAL_MODULE_TAGS := optional
LOCAL_LDLIBS := -llog
include $(BUILD_SHARED_LIBRARY)