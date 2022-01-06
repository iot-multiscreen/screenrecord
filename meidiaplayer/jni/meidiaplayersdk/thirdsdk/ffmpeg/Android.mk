LOCAL_PATH := $(call my-dir)

##-----------ffmpeg----------------------------------
FF_SRC_DIR =$(LOCAL_PATH)/$(TARGET_ARCH_ABI)/lib
$(warning $(FF_SRC_DIR))
include $(CLEAR_VARS)
LOCAL_MODULE    := ff_avformat
LOCAL_SRC_FILES :=  $(FF_SRC_DIR)/libavformat.so
LOCAL_EXPORT_C_INCLUDES :=  $(LOCAL_PATH)/$(TARGET_ARCH_ABI)/include/
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := ff_avutil
LOCAL_SRC_FILES :=  $(FF_SRC_DIR)/libavutil.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := ff_swresample
LOCAL_SRC_FILES :=  $(FF_SRC_DIR)/libswresample.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := ff_swscale
LOCAL_SRC_FILES :=  $(FF_SRC_DIR)/libswscale.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := ff_avcodec
LOCAL_SRC_FILES :=  $(FF_SRC_DIR)/libavcodec.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := ff_avfilter
LOCAL_SRC_FILES :=  $(FF_SRC_DIR)/libavfilter.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := ff_postproc
LOCAL_SRC_FILES :=  $(FF_SRC_DIR)/libpostproc.so
include $(PREBUILT_SHARED_LIBRARY)