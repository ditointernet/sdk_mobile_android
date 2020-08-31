package br.com.dito.ditosdk.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import br.com.dito.ditosdk.Dito
import br.com.dito.ditosdk.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONException
import org.json.JSONObject


class DitoMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        var notificationId: String? = null
        var reference: String? = null
        var deepLink: String? = null
        var message: String = ""
        var objData: JSONObject?
        var data: String? = null
        try {
            data = remoteMessage?.getData()?.getValue("data")
        } catch (e: Exception) {
            Log.d("missing-fields-notif", "Missing data from notification")
            return
        }
        try{
            objData = JSONObject(data)
        } catch (e: JSONException) {
            Log.d("invalid-data-notif", "Could not parse incoming data from notification as JSON")
            return
        }

        try {
            objData.get("notification")?.let {
                if (it is String) {
                    notificationId = it
                } else if (it is Integer) {
                    notificationId = it.toString()
                }
            }
        } catch (e: JSONException){
            Log.d("missing-fields-notif", "Missing notification identifier")
        }

        try {
            objData.get("reference")?.let {
                if (it is String) {
                    reference = it
                }
            }
        } catch (e: JSONException){
            Log.d("missing-fields-notif", "Missing notification reference")
        }

        try {
            message = objData.getJSONObject("details").get("message") as String
        } catch (e: Exception) {
            Log.d("empty-message-details", "There is no message in notification")
        }

        try {
            deepLink = objData.getJSONObject("details").get("link") as String
        } catch (e: Exception) {
            Log.d("empty-deeplink-details", "There is no deeplink in notification")
        }

        sendNotification(null, message, notificationId, reference, deepLink)
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        token?.let {
            if (!Dito.isInitialized()) {
                Dito.init(applicationContext, null)
            }
            Dito.registerDevice(it)
        }
    }

    private fun sendNotification(title: String?,
                                 message: String,
                                 notificationId: String?,
                                 reference: String?,
                                 deepLink: String?) {
        val intent = Intent(this, NotificationOpenedReceiver::class.java)

        intent.putExtra(Dito.DITO_NOTIFICATION_ID, notificationId)
        intent.putExtra(Dito.DITO_NOTIFICATION_REFERENCE, reference)
        intent.putExtra(Dito.DITO_DEEP_LINK, deepLink)

        val pendingIntent = PendingIntent.getBroadcast(this, 7, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val smallIcon = Dito.options?.iconNotification ?: applicationInfo.icon

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(smallIcon)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())

    }

    private fun getApplicationName(): String {
        return applicationInfo.loadLabel(packageManager).toString()
    }
}
