package br.com.dito.ditosdk.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.dito.ditosdk.Dito
import java.lang.Exception

class NotificationOpenedReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var notificationId: String = ""
        var reference: String = ""
        var deepLink: String = ""

        context?.let {
            if (!Dito.isInitialized()) {
                Dito.init(it.applicationContext, null)
            }
        }

        intent?.getStringExtra(Dito.DITO_NOTIFICATION_ID)?.let {
            notificationId = it
        }

        intent?.getStringExtra(Dito.DITO_NOTIFICATION_REFERENCE)?.let {
            reference = it
        }

        intent?.getStringExtra(Dito.DITO_DEEP_LINK)?.let {
            deepLink = it
        }

        if (reference != "" && notificationId != "") {
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

    private fun getIntent(context: Context, deepLink: String): Pair<Intent?, Intent?> {
        val packageName = context.packageName
        var intent = Dito.options?.contentIntent ?: context.packageManager?.
            getLaunchIntentForPackage(packageName)
        var deepLinkIntent: Intent? = null
        if (deepLink != "") {
            deepLinkIntent = Intent(deepLink)
        }
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        return Pair(intent, deepLinkIntent)
    }
}
