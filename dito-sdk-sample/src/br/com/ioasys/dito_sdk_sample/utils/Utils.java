package br.com.ioasys.dito_sdk_sample.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

public final class Utils {
    public static ProgressDialog showProgressCustom(Context context, Drawable icon, String title, boolean isCancelable, String message) {
        ProgressDialog progress = new ProgressDialog(context);
        progress.setIcon(icon);
        progress.setTitle(title);
        progress.setCancelable(isCancelable);
        progress.setMessage(message);

        return progress;
    }
}