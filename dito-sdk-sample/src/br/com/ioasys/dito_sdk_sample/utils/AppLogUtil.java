package br.com.ioasys.dito_sdk_sample.utils;

import android.util.Log;

public final class AppLogUtil {
    private static boolean LOG_ENABLED;

    /**
     * Method to enable/disable logging of application
     * 
     * @param log_enabled
     *            Flag to enable/disable the log
     */
    public static void setLogEnabled(final boolean log_enabled) {
        LOG_ENABLED = log_enabled;
    }

    public static void d(String tag, String msg) {
        if (LOG_ENABLED)
            Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (LOG_ENABLED)
            Log.d(tag, msg, tr);
    }

    public static void e(String tag, String msg) {
        if (LOG_ENABLED)
            Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (LOG_ENABLED)
            Log.e(tag, msg, tr);
    }

    public static void i(String tag, String msg) {
        if (LOG_ENABLED)
            Log.i(tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (LOG_ENABLED)
            Log.i(tag, msg, tr);
    }

    public static void v(String tag, String msg) {
        if (LOG_ENABLED)
            Log.v(tag, msg);
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (LOG_ENABLED)
            Log.v(tag, msg, tr);
    }

    public static void w(String tag, Throwable tr) {
        if (LOG_ENABLED)
            Log.w(tag, tr);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (LOG_ENABLED)
            Log.w(tag, msg, tr);
    }

    public static void w(String tag, String msg) {
        if (LOG_ENABLED)
            Log.w(tag, msg);
    }
}