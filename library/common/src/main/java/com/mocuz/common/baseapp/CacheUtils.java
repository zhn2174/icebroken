package com.mocuz.common.baseapp;

import android.app.Application;
import android.content.SharedPreferences;

import com.mocuz.common.commonutils.StringUtil;
import com.tencent.mmkv.MMKV;

/**
 * @author created by Wangw ;
 * @version 1.0
 * @data created time at 2018/2/1 ;
 * @Description 缓存管理工具
 */
public class CacheUtils {

    //sp文件名称
    private static final String FILE_NAME = "ICEBROKEN_CACHE";
    private static MMKV sharedPreferences = null;
    private static volatile String token = "";

    private CacheUtils() {
    }

    public static void init(Application app) {
        MMKV.initialize(app);
    }

    //获取sp对象
    public static SharedPreferences getSharedPrefer() {
        if (sharedPreferences == null)
            sharedPreferences = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, FILE_NAME);
        return sharedPreferences;
    }

    //获取Editor
    public static SharedPreferences.Editor getEditor() {
        return getSharedPrefer().edit();
    }

    public static void putString(String key, String value) {
        getEditor().putString(key, value);
    }

    public static String getString(String key, String defValue) {
        return getSharedPrefer().getString(key, defValue);
    }

    public static void putLong(String key, long value) {
        getEditor().putLong(key, value);
    }

    public static long getLong(String key, long defValue) {
        return getSharedPrefer().getLong(key, defValue);
    }

    public static void putInt(String key, int value) {
        getEditor().putInt(key, value);
    }

    public static int getInt(String key, int defValue) {
        return getSharedPrefer().getInt(key, defValue);
    }

    public static void putFloat(String key, float value) {
        getEditor().putFloat(key, value);
    }

    public static float getFloat(String key, float defValue) {
        return getSharedPrefer().getFloat(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        getEditor().putBoolean(key, value);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getSharedPrefer().getBoolean(key, defValue);
    }

    /**
     * 清除所有SP数据
     */
    public static void clearSPCache() {
        getEditor().clear();
    }

    /**
     * 移除指定key的数据
     *
     * @param key
     */
    public static void removeSPCache(String key) {
        getEditor().remove(key);
    }

    /**
     * Token操作
     *
     * @param value
     */
    public static void putToken(String value) {
        token = value;
        putString("Token", value);
    }

    public static String getToken() {
        if (StringUtil.isNotBlank(token)) {
            return token;
        }
        return getString("Token", token);
    }
}
