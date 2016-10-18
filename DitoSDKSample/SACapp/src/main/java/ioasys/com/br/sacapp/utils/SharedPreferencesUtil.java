package ioasys.com.br.sacapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.Map;

public final class SharedPreferencesUtil {
    public static final String TAG = "shared_preferences_helper";

    /**
     * Método para ler todas as chaves/valores de um determinando arquivo de
     * preferencias
     *
     * @param context Context of application
     * @param file    Path of the preferences file to be read
     * @return Map key/value of the preferences contained in the file specified
     */
    public static Map<String, ?> readAll(Context context, String file) {
        Map<String, ?> prefs = null;

        try {
            SharedPreferences sharedPrefs = context.getApplicationContext().getSharedPreferences(file, Context.MODE_PRIVATE);
            prefs = sharedPrefs.getAll();
        } catch (Exception e) {
            prefs = null;
            Log.e(TAG, e.getMessage(), e);
        }

        return prefs;
    }

    /**
     * Method to read a boolean value of a given key in a given file preferences
     *
     * @param context      Context of application
     * @param file         Name of preferences file
     * @param key          Name of key to be read
     * @param defaultValue Value default to returned case the key not exists
     * @return true | false
     */
    public static boolean read(Context context, String file, String key, boolean defaultValue) {
        boolean value = false;

        try {
            SharedPreferences sharedPrefs = context.getApplicationContext().getSharedPreferences(file, Context.MODE_PRIVATE);
            value = sharedPrefs.getBoolean(key, defaultValue);
        } catch (Exception e) {
            value = defaultValue;
            Log.e(TAG, e.getMessage(), e);
        }

        return value;
    }

    /**
     * Method to read a String value of a given key in a given preferences file
     *
     * @param context      Context of application
     * @param file         Name of preferences file
     * @param key          Name of key to be read
     * @param defaultValue Value default to returned case the key not exists
     * @return String
     */
    public static String read(Context context, String file, String key, String defaultValue) {
        String value = null;

        try {
            SharedPreferences sharedPrefs = context.getApplicationContext().getSharedPreferences(file, Context.MODE_PRIVATE);
            value = sharedPrefs.getString(key, defaultValue);
        } catch (Exception e) {
            value = defaultValue;
            Log.e(TAG, e.getMessage(), e);
        }

        return value;
    }

    /**
     * Method for reading a long value of a given key in a given file
     * preferences
     *
     * @param context      Context of application
     * @param file         Name of preferences file
     * @param key          Name of key to be read
     * @param defaultValue Value default to returned case the key not exists
     * @return long
     */
    public static long read(Context context, String file, String key, long defaultValue) {
        long value = Long.MIN_VALUE;

        try {
            SharedPreferences sharedPrefs = context.getApplicationContext().getSharedPreferences(file, Context.MODE_PRIVATE);
            value = sharedPrefs.getLong(key, defaultValue);
        } catch (Exception e) {
            value = defaultValue;
            Log.e(TAG, e.getMessage(), e);
        }

        return value;
    }

    /**
     * Method to write a boolean value in a given key in a given file
     * preferences
     *
     * @param context Context of application
     * @param file    Name of preferences file
     * @param key     Name of key to be read
     * @param value   Value to be saved in the key
     * @return true | false
     */
    public static boolean write(Context context, String file, String key, boolean value) {
        boolean sucess = false;

        try {
            SharedPreferences sharedPrefs = context.getApplicationContext().getSharedPreferences(file, Context.MODE_PRIVATE);
            Editor editor = sharedPrefs.edit();
            editor.putBoolean(key, value);
            editor.commit();
            sucess = true;
        } catch (Exception e) {
            sucess = false;
            Log.e(TAG, e.getMessage(), e);
        }

        return sucess;
    }

    /**
     * Method to write a boolean value in a given key in a given file
     * preferences
     *
     * @param context Context of application
     * @param file    Name of preferences file
     * @param key     Name of key to be read
     * @param value   Value to be saved in the key
     * @return true | false
     */
    public static boolean write(Context context, String file, String key, String value) {
        boolean sucess = false;

        try {
            SharedPreferences sharedPrefs = context.getApplicationContext().getSharedPreferences(file, Context.MODE_PRIVATE);
            Editor editor = sharedPrefs.edit();
            editor.putString(key, value);
            editor.commit();
            sucess = true;
        } catch (Exception e) {
            sucess = false;
            Log.e(TAG, e.getMessage(), e);
        }

        return sucess;
    }

    /**
     * Method to write a boolean value in a given key in a given file
     * preferences
     *
     * @param context Context of application
     * @param file    Name of preferences file
     * @param key     Name of key to be read
     * @param value   Value to be saved in the key
     * @return true | false
     */
    public static boolean write(Context context, String file, String key, long value) {
        boolean sucess = false;

        try {
            SharedPreferences sharedPrefs = context.getApplicationContext().getSharedPreferences(file, Context.MODE_PRIVATE);
            Editor editor = sharedPrefs.edit();
            editor.putLong(key, value);
            editor.commit();
            sucess = true;
        } catch (Exception e) {
            sucess = false;
            Log.e(TAG, e.getMessage(), e);
        }

        return sucess;
    }

    /**
     * Method to remove a particular key of a given file preferences
     *
     * @param context Context of application
     * @param file    Name of preferences file
     * @param key     Name of key to be read
     * @return true | false
     */
    public static boolean remove(Context context, String file, String key) {
        boolean sucess = false;

        try {
            SharedPreferences sharedPrefs = context.getApplicationContext().getSharedPreferences(file, Context.MODE_PRIVATE);
            Editor editor = sharedPrefs.edit();
            editor.remove(key);
            editor.commit();
            sucess = true;
        } catch (Exception e) {
            sucess = false;
            Log.e(TAG, e.getMessage(), e);
        }

        return sucess;
    }
}