#include <jni.h>
#include "libtest.h"

JNIEXPORT jstring JNICALL Java_com_mytest_JNIActivity_getMessage(
    JNIEnv *env,
    jobject thisObj
) {
    int i = testFunction();
    return env->NewStringUTF("Hello from native code!");
}