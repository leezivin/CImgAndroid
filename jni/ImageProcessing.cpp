#define cimg_display 0
#define cimg_use_jpeg
#define cimg_use_png

#include "CImg.h"
#include <jni.h>

using namespace cimg_library;

JNIEXPORT jstring JNICALL Java_com_mytest_JNIActivity_getMessage(
    JNIEnv *env,
    jobject thisObj
) {
    return env->NewStringUTF("Hello from native code!");
}