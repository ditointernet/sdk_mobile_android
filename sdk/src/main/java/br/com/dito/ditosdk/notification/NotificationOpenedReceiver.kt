package br.com.dito.ditosdk.notification

import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import br.com.dito.ditosdk.Dito

class NotificationOpenedReceiver : BroadcastReceiver() {

    companion object {
        const val DITO_NOTIFICATION_ID = "dito_notification_id"
        const val DITO_NOTIFICATION_REFERENCE = "dito_notification_reference"
        const val DITO_DEEP_LINK = "dito_deep_link"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { ctx ->
            val notificationId = intent?.getStringExtra(DITO_NOTIFICATION_ID)
            val reference = intent?.getStringExtra(DITO_NOTIFICATION_REFERENCE)
            val deepLink = intent?.getStringExtra(DITO_DEEP_LINK)

            if (!Dito.isInitialized()) {
                Dito.init(ctx.applicationContext, null)
            }

            if (reference != null && notificationId != null) {
                Dito.notificationRead(notificationId, reference)
            }

            getIntent(ctx, deepLink)?.let { safeIntent ->
                try {
                    ctx.startActivity(safeIntent)
                } catch (e: ActivityNotFoundException) {
                    Log.d("debug-lamoda", "Error starting activity: No activity found for intent")
                } catch (e: Exception) {
                    Log.d("debug-lamoda", "Error starting activity: ${e.message}")
                }
            }
        }
    }

    private fun getIntent(context: Context, deepLink: String?): Intent? {
        val intent = Dito.options?.contentIntent
            ?: context.packageManager?.getLaunchIntentForPackage(context.packageName)
        if (intent == null) return null

        intent.apply {
            putExtra(DITO_DEEP_LINK, deepLink)
            if (Dito.getHibridMode() == "ON") {
                addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY)
            } else {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
        return intent
    }
}