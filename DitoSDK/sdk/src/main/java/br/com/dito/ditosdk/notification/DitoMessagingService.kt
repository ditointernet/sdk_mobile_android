package br.com.dito.ditosdk.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import br.com.dito.ditosdk.Dito
import br.com.dito.ditosdk.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject


class DitoMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        var notificationId: String = ""
        var reference: String = ""
        var deepLink: String = ""
        val data = remoteMessage?.getData()?.getValue("data")
        val objData = JSONObject(data)

        objData.get("notification")?.let {
            if (it is String){
                notificationId = it
            } else if (it is Integer) {
                notificationId = it.toString()
            } else {
                notificationId = ""
            }
        }

        try{
            objData.get("reference")?.let {
                if (it is String) {
                    reference = it
                }
            }
        } catch (e: Exception) {}

        val message = objData.getJSONObject("details").get("message") as String

        try {
            deepLink = objData.getJSONObject("details").get("link") as String
        } catch (e: Exception) {}

        if (reference != "" && notificationId != "") {
            sendNotification(null, message, notificationId, reference, deepLink)
        }
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
                                 message: String?,
                                 notificationId: String,
                                 reference: String,
                                 deepLink: String) {
        val intent = Intent(this, NotificationOpenedReceiver::class.java)

        intent.putExtra(Dito.DITO_NOTIFICATION_ID, notificationId)
        intent.putExtra(Dito.DITO_NOTIFICATION_REFERENCE, reference)
        intent.putExtra(Dito.DITO_DEEP_LINK, deepLink)

        val pendingIntent = PendingIntent.getBroadcast(this, 7, intent, 0)

        val smallIcon = Dito.options?.iconNotification ?: applicationInfo.icon

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(smallIcon)
                .setContentTitle(title ?: getApplicationName())
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

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
