package com.beknumonov.noteapp2.util;

public interface PreferenceHelper {

    void setValue(String key, Object value);

    <T> Object getValue(Class<T> tClass, String key, Object defaulValue);

    void removeKey(String key);

    void clear();

}
