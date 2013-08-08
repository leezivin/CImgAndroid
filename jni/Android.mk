LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_C_INCLUDES := $(LOCAL_PATH)/hello
LOCAL_MODULE    := myjni
LOCAL_SRC_FILES := HelloWorld.cpp

include $(BUILD_SHARED_LIBRARY)