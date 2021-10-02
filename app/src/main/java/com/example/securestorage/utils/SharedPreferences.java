package com.example.securestorage.utils;

import android.content.Context;

import java.util.Map;
import java.util.Set;

public class SharedPreferences {
    private static final String SETTINGS_NAME = "save_data_shared_preferences";
    private static SharedPreferences sSharedPrefs;
    private android.content.SharedPreferences mPref;
    private android.content.SharedPreferences.Editor mEditor;
    private boolean mBulkUpdate = false;


    private SharedPreferences(Context context) {
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (sSharedPrefs == null) {
            sSharedPrefs = new SharedPreferences(context.getApplicationContext());
        }
    }

    public static SharedPreferences getInstance() {
        if (sSharedPrefs != null) {
            return sSharedPrefs;
        }
        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");
    }

    public void putString(String key, String val) {
        doEdit();
        mEditor.putString(key, val);
        doCommit();
    }

    public void putInt(String key, int val) {
        doEdit();
        mEditor.putInt(key, val);
        doCommit();
    }

    public void putBoolean(String key, boolean val) {
        doEdit();
        mEditor.putBoolean(key, val);
        doCommit();
    }

    public void putFloat(String key, float val) {
        doEdit();
        mEditor.putFloat(key, val);
        doCommit();
    }

    /**
     * Convenience method for storing doubles.
     * <p>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The name of the preference to store.
     * @param val The new value for the preference.
     */
    public void putDouble(String key, double val) {
        doEdit();
        mEditor.putString(key, String.valueOf(val));
        doCommit();
    }

    public void putLong(String key, long val) {
        doEdit();
        mEditor.putLong(key, val);
        doCommit();
    }

    public String getString(String key, String defaultValue) {
        return mPref.getString(key, defaultValue);
    }

    public String getString(String key) {
        return mPref.getString(key, null);
    }

    public int getInt(String key) {
        return mPref.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return mPref.getInt(key, defaultValue);
    }

    public long getLong(String key) {
        return mPref.getLong(key, 0);
    }

    public long getLong(String key, long defaultValue) {
        return mPref.getLong(key, defaultValue);
    }

    public float getFloat(String key) {
        return mPref.getFloat(key, 0);
    }

    public float getFloat(String key, float defaultValue) {
        return mPref.getFloat(key, defaultValue);
    }

    /**
     * Convenience method for retrieving doubles.
     * <p>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The name of the preference to fetch.
     */
    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    /**
     * Convenience method for retrieving doubles.
     * <p>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The name of the preference to fetch.
     */
    public double getDouble(String key, double defaultValue) {
        try {
            return Double.valueOf(mPref.getString(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mPref.getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return mPref.getBoolean(key, false);
    }

    /**
     * Remove keys from SharedPreferences.
     *
     * @param keys The name of the key(s) to be removed.
     */
    public void remove(String... keys) {
        doEdit();
        for (String key : keys) {
            mEditor.remove(key);
        }
        doCommit();
    }

    /**
     * Remove all keys from SharedPreferences.
     */
    public void clear() {
        doEdit();
        mEditor.clear();
        doCommit();
    }

    public void edit() {
        mBulkUpdate = true;
        mEditor = mPref.edit();
    }

    public void commit() {
        mBulkUpdate = false;
        mEditor.commit();
        mEditor = null;
    }

    private void doEdit() {
        if (!mBulkUpdate && mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doCommit() {
        if (!mBulkUpdate && mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }

    public boolean checkKeyExist(String key) {
        doEdit();
        return mPref.contains(key);
    }

    public void putStringSet(String key, Set<String> setValue) {
        doEdit();
        mEditor.putStringSet(key, setValue);
        doCommit();
    }

    public Set<String> getStringSet(String key, Set<String> defValue) {
        return mPref.getStringSet(key, defValue);
    }

    public Map<String, ?> getAllKeys(){
        return mPref.getAll();
    }
}
