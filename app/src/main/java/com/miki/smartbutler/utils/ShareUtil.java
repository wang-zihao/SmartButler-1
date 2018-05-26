package com.miki.smartbutler.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 包名:      com.miki.smartbutler.utils
 * 文件名:     ShareUtil.java
 * 创建者:     王子豪
 * 创建时间:   2018/4/22 17:18
 * 描述:      SharedPreference工具类
 */

public class ShareUtil {

    public static final String NAME = "config";

    //write a String type
    public static void putString(Context context, String key, String values) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, values).commit();
    }

    //get a String type
    public static String getString(Context context, String key, String defValues) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValues);
    }

    //write a int type
    public static void putInt(Context context, String key, int values) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, values).commit();
    }

    //get a int type
    public static int getInt(Context context, String key, int defValues) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defValues);
    }

    //write a boolean type
    public static void putBoolean(Context context, String key, boolean values) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, values).commit();
    }

    //get a boolean type
    public static Boolean getBoolean(Context context, String key, boolean defValues) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValues);
    }

    //delete one
    public static void deleteShare(Context context,String key) {
        SharedPreferences sp = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    //delete All
    public static void deleteAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }

}
