package br.com.dito.ditosdk

import android.content.Context
import android.content.pm.PackageManager
import br.com.dito.ditosdk.service.RemoteService
import br.com.dito.ditosdk.tracking.Tracker
import br.com.dito.ditosdk.tracking.TrackerOffline
import br.com.dito.ditosdk.tracking.TrackerRetry

object Dito {

    private lateinit var apiKey: String
    private lateinit var hibridMode: String
    private lateinit var apiSecret: String
    private lateinit var tracker: Tracker

    const val DITO_NOTIFICATION_ID = "br.com.dito.ditosdk.DITO_NOTIFICATION_ID"
    const val DITO_NOTIFICATION_REFERENCE = "br.com.dito.ditosdk.DITO_NOTIFICATION_REFERENCE"
    const val DITO_DEEP_LINK = "br.com.dito.ditosdk.DITO_DEEP_LINK"

    var options: Options? = null

    /**
     *
     * @param context
     * @param options
     */
    fun init(context: Context?, options: Options?) {
        this.options = options

        val appInfo = context?.packageManager?.getApplicationInfo(
            context.packageName,
            PackageManager.GET_META_DATA
        )

        appInfo?.metaData?.let {
            apiKey = it.getString("br.com.dito.API_KEY", "")
            apiSecret = it.getString("br.com.dito.API_SECRET", "")
            hibridMode = it.getString("br.com.dito.HIBRID_MODE", "OFF")

            if (apiKey.isEmpty() || apiSecret.isEmpty()) {
                throw RuntimeException("É preciso configurar API_KEY e API_SECRET no AndroidManifest.")
            }

            val trackerOffline = TrackerOffline(context)

            tracker = Tracker(apiKey, apiSecret, trackerOffline)

            val trackerRetry = TrackerRetry(tracker, trackerOffline, options?.retry ?: 5)
            trackerRetry.uploadEvents()
        }
    }

    /**
     * @param identify
     */
    fun identify(identify: Identify?, callback: (() -> Unit)?) {
        identify?.let { tracker.identify(it, RemoteService.loginApi(), callback) }
    }

    /**
     * @param event
     */
    fun track(event: Event?) {
        event?.let { tracker.event(it, RemoteService.eventApi()) }
    }

    /**
     * @param token
     */
    fun registerDevice(token: String?) {
        token?.let { tracker.registerToken(it, RemoteService.notificationApi()) }
    }

    /**
     * @param token
     */
    fun unregisterDevice(token: String?) {
        token?.let { tracker.unregisterToken(it, RemoteService.notificationApi()) }
    }

    /**
     * @param notification
     */
    fun notificationRead(notification: String?, reference: String?) {
        if (reference?.isEmpty() == true) {
            return
        }

        notification?.let {
            tracker.notificationRead(
                it,
                RemoteService.notificationApi(),
                reference.toString()
            )
        }
    }

    internal fun isInitialized(): Boolean {
        return apiKey.isNotEmpty() && apiSecret.isNotEmpty()
    }

    fun getHibridMode(): String {
        return hibridMode;
    }

}
