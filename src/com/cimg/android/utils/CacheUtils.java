package com.cimg.android.utils;

import android.content.Context;

/**
 * Created by seven on 8/17/13.
 */
public class CacheUtils {
    private CacheUtils(){
    }

    public static String getCachePath(final Context context) {
        if (context.getExternalCacheDir() != null){
            return context.getExternalCacheDir().getAbsolutePath() + "/";
        } else {
            return context.getCacheDir().getAbsolutePath() + "/";
        }
    }
}
