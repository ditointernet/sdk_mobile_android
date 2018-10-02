package br.com.dito.ditosdk

import android.content.Context
import android.content.pm.PackageManager
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import br.com.dito.ditosdk.offline.DitoSqlHelper
import br.com.dito.ditosdk.tracking.TrackerOffline
import br.com.dito.ditosdk.service.RemoteService
import br.com.dito.ditosdk.tracking.Tracker
import br.com.dito.ditosdk.tracking.TrackerRetry
import org.jetbrains.annotations.NotNull

object Dito  {

    private lateinit var apiKey: String
    private lateinit var apiSecret: String
    private lateinit var tracker: Tracker

   const val  DITO_NOTIFICATION_ID = "br.com.dito.ditosdk.DITO_NOTIFICATION_ID"

    var options: Options? = null

    /**
     *
     * @param context
     * @param options
     */
    fun init(@NonNull context: Context, @Nullable options: Options?) {
        this.options = options

        val appInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)

        appInfo.metaData?.let {
            apiKey = it.getString("br.com.dito.API_KEY", "")
            apiSecret = it.getString("br.com.dito.API_SECRET", "")

            if (apiKey.isNullOrEmpty() || apiSecret.isNullOrEmpty()) {
                throw RuntimeException("É preciso configurar API_KEY e API_SECRET no AndroidManifest.")
            }

            val trackerOffline = TrackerOffline(DitoSqlHelper.getInstance(context))
            tracker = Tracker(apiKey, apiSecret, trackerOffline)

//            val trackerRetry = TrackerRetry(tracker, trackerOffline, options?.retry ?: 5)
//            trackerRetry.uploadEvents()
        }
    }

    /**
     * @param identify
     */
    fun identify (@NonNull identify: Identify) {
        tracker.identify(identify, RemoteService.loginApi())
    }

    /**
     * @param event
     */
    fun track(@NotNull event: Event) {
        tracker.event(event, RemoteService.eventApi())
    }

    /**
     * @param token
     */
    fun registerDevice(@NotNull token: String) {
        tracker.registerToken(token, RemoteService.notificationApi())
    }

    /**
     * @param token
     */
    fun unregisterDevice(@NotNull token: String) {
        tracker.unregisterToken(token, RemoteService.notificationApi())
    }

    /**
     * @param notification
     */
    fun notificationRead(@NotNull notification: String) {
        tracker.notificationRead(notification, RemoteService.notificationApi())
    }

    internal fun isInitialized(): Boolean {
        return apiKey != null && apiSecret != null
    }
}