MY_LOCAL_PATH := $(call my-dir)

LOCAL_PATH := $(MY_LOCAL_PATH)
include $(LOCAL_PATH)/mediaplayersdk/thirdsdk/ffmpeg/Android.mk

LOCAL_PATH := $(MY_LOCAL_PATH)
include $(LOCAL_PATH)/base/Android.mk

LOCAL_PATH := $(MY_LOCAL_PATH)
include $(LOCAL_PATH)/mediaplayersdk/Android.mk

LOCAL_PATH := $(MY_LOCAL_PATH)
include $(LOCAL_PATH)/mediaplayersdkjni/Android.mk
