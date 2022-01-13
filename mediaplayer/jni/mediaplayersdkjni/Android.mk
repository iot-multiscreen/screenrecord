LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := libmediaplayer_native
LOCAL_SRC_FILES :=  $(LOCAL_PATH)/com_mediaplayer_jni.cpp
LOCAL_C_INCLUDES := $(LOCAL_PATH) \
                    $(LOCAL_PATH)/../
LOCAL_MODULE_TAGS := optional
LOCAL_LDLIBS := -llog -landroid
include $(BUILD_SHARED_LIBRARY)