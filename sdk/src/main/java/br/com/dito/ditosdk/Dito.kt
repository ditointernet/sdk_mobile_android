package br.com.dito.ditosdk

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import br.com.dito.ditosdk.service.RemoteService
import br.com.dito.ditosdk.tracking.Tracker
import org.jetbrains.annotations.NotNull

object Dito  {

    private lateinit var apiKey: String
    private lateinit var apiSecret: String
    private lateinit var tracker: Tracker

    var options: Options? = null


    /**
     *
     * @param context
     * @param options
     */
    fun init(@NonNull context: Context, @Nullable options: Options?) {
        val appInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        appInfo.metaData?.let {
            apiKey = it.getString("br.com.dito.API_KEY", "")
            apiSecret = it.getString("br.com.dito.API_SECRET", "")

            if (apiKey.isNullOrEmpty() || apiSecret.isNullOrEmpty()) {
                throw RuntimeException("É preciso configurar API_KEY e API_SECRET no AndroidManifest.")
            }

            tracker = Tracker(apiKey, apiSecret)
        }

        this.options = options
    }


    fun identify (@NonNull identify: Identify) {
        tracker.identify(identify, RemoteService.loginApi())
    }

    fun tracker(@NotNull event: Event) {
        tracker.event(event, RemoteService.eventApi())
    }

    fun registerDevice(@NotNull token: String) {
        tracker.registerToken(token, RemoteService.notificationApi())
    }

    fun unregisterDevice(@NotNull token: String) {
        tracker.unregisterToken(token, RemoteService.notificationApi())
    }

    fun notificationRead(@NotNull notification: String) {

    }

}