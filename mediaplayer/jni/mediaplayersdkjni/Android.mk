LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := libmediaplayer_native
LOCAL_SRC_FILES :=  $(LOCAL_PATH)/com_mediaplayer_jni.cpp \
                    $(LOCAL_PATH)/interface/IMediaplayerBaseInterface.cpp \
                    $(LOCAL_PATH)/interface/MediaplayerInterface.cpp

LOCAL_C_INCLUDES := $(LOCAL_PATH) \
                    $(LOCAL_PATH)/interface \
                    $(LOCAL_PATH)/../
LOCAL_MODULE_TAGS := optional
LOCAL_LDLIBS := -llog -landroid
include $(BUILD_SHARED_LIBRARY)