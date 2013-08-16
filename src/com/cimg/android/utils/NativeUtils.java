package com.cimg.android.utils;

/**
 * Created by seven on 8/16/13.
 */
public class NativeUtils {
    static {
        System.loadLibrary("ImageProcessing");
    }

    private NativeUtils(){
    }

    public static native void generatePrimitives(String resultPath);
}
