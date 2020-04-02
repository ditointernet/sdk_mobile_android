package br.com.dito.ditosdk.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.dito.ditosdk.Dito
import java.lang.Exception

class NotificationOpenedReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var notificationId: String? = intent?.getStringExtra(Dito.DITO_NOTIFICATION_ID)
        var reference: String? = intent?.getStringExtra(Dito.DITO_NOTIFICATION_REFERENCE)
        var deepLink: String? = intent?.getStringExtra(Dito.DITO_DEEP_LINK)

        context?.let {
            if (!Dito.isInitialized()) {
                Dito.init(it.applicationContext, null)
            }
        }

        if (reference != null && notificationId != null) {
            Dito.notificationRead(notificationId, reference)
        }

        context?.let {
            val (defaultIntent, deepLinkIntent) = getIntent(it, deepLink)
            try {
                context.startActivity(deepLinkIntent)
            } catch (e: Exception)  {
                context.startActivity(defaultIntent)
            }
        }
    }

    private fun getIntent(context: Context, deepLink: String?): Pair<Intent?, Intent?> {
        val packageName = context.packageName
        var intent = Dito.options?.contentIntent ?: context.packageManager?.
            getLaunchIntentForPackage(packageName)
        var deepLinkIntent: Intent? = null
        if (deepLink != null) {
            deepLinkIntent = Intent(deepLink)
        }
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        return Pair(intent, deepLinkIntent)
    }
}
