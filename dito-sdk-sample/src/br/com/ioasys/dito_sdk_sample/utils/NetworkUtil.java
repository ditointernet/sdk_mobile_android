package br.com.ioasys.dito_sdk_sample.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;

public final class NetworkUtil {
    private static final String PLAY_STORE_DETAIL_APP_URL = "https://play.google.com/store/apps/details?id=";

    public static void openGooglePlay(Context context, String packageName, int transitionAnimation) {
        String urlGooglePlay = PLAY_STORE_DETAIL_APP_URL + packageName;
        Uri uri = Uri.parse(urlGooglePlay);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        context.startActivity(intent);
        ((ActionBarActivity) context).overridePendingTransition(transitionAnimation, 0);
    }
}
