package br.com.dito.ditosdk

import android.content.Context
import android.content.pm.PackageManager
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import br.com.dito.ditosdk.service.RemoteService
import br.com.dito.ditosdk.tracking.Tracker

object Dito  {

    private lateinit var apiKey: String
    private lateinit var apiSecret: String
    private lateinit var tracker: Tracker

    private  var dryRun: Boolean = false

    var options: Options? = null


    fun init(@NonNull context: Context, @Nullable options: Options?) {
        val appInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        appInfo.metaData?.let {
            apiKey = it.getString("br.com.dito.API_KEY")
            apiSecret = it.getString("br.com.dito.API_SECRET")

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

    fun track(action: String, revenue: String?, data: CustomData?) {

    }

    fun registerDevice(token: String) {

    }

    fun unregisterDevice(token: String) {

    }

    fun notificationRead(notification: String) {

    }

    fun setDryRun(value: Boolean) {
        this.dryRun = value
    }

}