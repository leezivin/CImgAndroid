package com.cimg.android.utils;

import android.content.Context;
import android.os.Environment;

/**
 * Created by seven on 8/17/13.
 */
public class CacheUtils {
    private CacheUtils(){
    }

    public static String getCachePath(final Context context) {
        if (true) return "/sdcard/";
        if (context.getExternalCacheDir() != null){
            return context.getExternalCacheDir() + "/";
        } else {
            return context.getCacheDir().getAbsolutePath() + "/";
        }
    }
}
