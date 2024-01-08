package com.beknumonov.noteapp2.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.beknumonov.noteapp2.BuildConfig;

public class PreferencesManager implements PreferenceHelper {


    private static String SHARED_PREFERENCE_NAME = BuildConfig.APPLICATION_ID + ".local";

    private SharedPreferences mPreference;


    private static PreferencesManager mInstance;

    public PreferencesManager(Context context) {
        mPreference = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static PreferencesManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new PreferencesManager(context);

        return mInstance;
    }


    @Override
    public void setValue(String key, Object value) {

        if (value.getClass().equals(String.class)) {
            mPreference.edit().putString(key, (String) value).apply();
        } else if (value.getClass().equals(Boolean.class)) {
            mPreference.edit().putBoolean(key, (Boolean) value).apply();
        }
    }

    @Override
    public <T> Object getValue(Class<T> tClass, String key, Object defaulValue) {
        if (tClass.equals(String.class)) {
            return mPreference.getString(key, (String) defaulValue);
        } else if (tClass.equals(Boolean.class)) {
            return mPreference.getBoolean(key, (Boolean) defaulValue);
        }

        return defaulValue;
    }

    @Override
    public void removeKey(String key) {
        mPreference.edit().remove(key).apply();
    }

    @Override
    public void clear() {
        mPreference.edit().clear().apply();
    }
}
