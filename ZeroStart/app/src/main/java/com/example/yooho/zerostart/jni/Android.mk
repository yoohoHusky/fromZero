LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := desc
LOCAL_SRC_FILES := JniCFile.c
include $(BUILD_SHARED_LIBRARY)