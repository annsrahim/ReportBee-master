package com.technocarrot.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.technocarrot.Config;


/**
 * Created by Anns on 06/07/17.
 */

public class SharedPreferenceUtils {
    public static void setIntDataInShare(Context context, String key, int value)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(Config.MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public static int getIntDataInShare(Context context, String key)
    {
        SharedPreferences prefs = context.getSharedPreferences(Config.MY_PREFS_NAME,context.MODE_PRIVATE);
        return prefs.getInt(key,0);
    }
    public static void setStringDataInShare(Context context, String key, String value)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(Config.MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }
    public static String getStringDataInShare(Context context, String key)
    {
        SharedPreferences prefs = context.getSharedPreferences(Config.MY_PREFS_NAME, context.MODE_PRIVATE);
        return  prefs.getString(key, "");
    }


}
