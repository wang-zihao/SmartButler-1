package com.miki.smartbutler.utils;

import android.util.Log;

/**
 * 包名:      com.miki.smartbutler.utils
 * 文件名:     L.java
 * 创建者:     王子豪
 * 创建时间:   2018/4/20 23:19
 * 描述:      Log封装类
 */

public class L {
    public static final boolean DEBUG = true;

    public static final String TAG = "SmartButler";

    public static void d(String text) {
        if (DEBUG) {
            Log.d(TAG, text);
        }
    }

    public static void i(String text) {
        if (DEBUG) {
            Log.i(TAG, text);
        }
    }

    public static void w(String text) {
        if (DEBUG) {
            Log.w(TAG, text);
        }
    }

    public static void e(String text) {
        if (DEBUG) {
            Log.e(TAG, text);
        }
    }
}
