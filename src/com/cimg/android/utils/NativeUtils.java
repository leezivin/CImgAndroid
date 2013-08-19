package com.cimg.android.utils;

/**
 * Created by seven on 8/16/13.
 */
public class NativeUtils {
    static {
        System.loadLibrary("imageprocessing");
    }

    private NativeUtils(){
    }

    public static native String getMessage();

    public static native void generatePrimitives(
        String resultPath
    );

    public static native void changeColorBalance(
        String sourcePath,
        String resultPath,
        float r,
        float g,
        float b
    );

    public static native void processingBlocks(
        String sourcePath,
        String resultPath
    );

}
