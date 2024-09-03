package br.com.dito.ditosdk.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import br.com.dito.ditosdk.Dito
import br.com.dito.ditosdk.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONException
import org.json.JSONObject

class DitoMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            lateinit var notificationId: String
            lateinit var reference: String
            lateinit var deepLink: String
            var title = ""
            var message = ""
            var objData: JSONObject?
            lateinit var data: String

            try {
                remoteMessage.data.getValue("data").also { data = it }
            } catch (e: Exception) {
                Log.d("missing-fields-notif", "Missing data from notification")
                return
            }
            try {
                JSONObject(data).also { objData = it }
            } catch (e: JSONException) {
                Log.d(
                    "invalid-data-notif",
                    "Could not parse incoming data from notification as JSON"
                )
                return
            }

            objData?.get("notification")?.let {
                if (it is String) {
                    it.also { notificationId = it }
                } else if (it is Int) {
                    notificationId = it.toString()
                } else {
                    Log.d(
                        "invalid-data-notif",
                        "Could not parse incoming data from notification as JSON"
                    )
                }
            }


            objData?.get("reference")?.let {
                if (it is String) {
                    reference = it
                }
            }

            try {
                (objData?.getJSONObject("details")?.get("title") as String).also { title = it }
            } catch (e: Exception) {
                Log.d("empty-title-details", "There is no title in notification")
            }

            try {
                (objData?.getJSONObject("details")?.get("message") as String).also { message = it }
            } catch (e: Exception) {
                Log.d("empty-message-details", "There is no message in notification")
            }

            try {
                (objData?.getJSONObject("details")?.get("link") as String).also { deepLink = it }
            } catch (e: Exception) {
                Log.d("empty-deeplink-details", "There is no deeplink in notification")
            }

            sendNotification(
                title = title,
                message = message,
                notificationId = notificationId,
                reference = reference,
                deepLink = deepLink
            )
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        token.let {
            if (!Dito.isInitialized()) {
                Dito.init(applicationContext, null)
            }
            Dito.registerDevice(it)
        }
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification(
        title: String?,
        message: String,
        notificationId: String?,
        reference: String?,
        deepLink: String?
    ) {
        val intent = Intent(this, NotificationOpenedReceiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        intent.putExtra(Dito.DITO_NOTIFICATION_ID, notificationId)
        intent.putExtra(Dito.DITO_NOTIFICATION_REFERENCE, reference)
        intent.putExtra(Dito.DITO_DEEP_LINK, deepLink)

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        val smallIcon = Dito.options?.iconNotification ?: applicationInfo.icon

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        var notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(smallIcon)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setSound(defaultSoundUri)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun getApplicationName(): String {
        return applicationInfo.loadLabel(packageManager).toString()
    }
}
