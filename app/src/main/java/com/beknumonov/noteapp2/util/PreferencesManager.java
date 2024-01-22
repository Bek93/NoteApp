package com.beknumonov.noteapp2.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.beknumonov.noteapp2.BuildConfig;
import com.beknumonov.noteapp2.model.User;
import com.google.gson.Gson;

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


    // setValue accept two variables: Key as String, Value as Object (Object can be String, Boolean, etc)
    @Override
    public void setValue(String key, Object value) {
        // checking value type:
        if (value.getClass().equals(String.class)) {
            // if it is String, save as String
            mPreference.edit().putString(key, (String) value).apply();
            // mPreference is instance of PreferenceManager which created in first time call.
            // it is static variables which value is set during runtime.
            // it can be life during application's runtime.
        } else if (value.getClass().equals(Boolean.class)) {
            // else if it is Boolean, save as Boolean.
            mPreference.edit().putBoolean(key, (Boolean) value).apply();
        } else if (value.getClass().equals(User.class)) {

            String json = new Gson().toJson((User) value);
            setValue(key, json);
        }
    }

    @Override
    public <T> Object getValue(Class<T> tClass, String key, Object defaultValue) {
        if (tClass.equals(String.class)) {
            return mPreference.getString(key, (String) defaultValue);
        } else if (tClass.equals(Boolean.class)) {
            return mPreference.getBoolean(key, (Boolean) defaultValue);
        } else if (tClass.equals(User.class)) {
            String json = mPreference.getString(key, "");
            if (!json.isEmpty()) {
                User user = new Gson().fromJson(json, User.class);
                return user;
            }
        }

        return defaultValue;
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
