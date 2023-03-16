package br.com.dito.ditosdk.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
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
            val intent = getIntent(it, deepLink)
            try {
                context.startActivity(intent)
            } catch (e: Exception)  {Log.d("debug-lamoda", "Erro")}
        }
    }

    private fun getIntent(context: Context, deepLink: String?): Intent? {
        val packageName = context.packageName
        val intent = Dito.options?.contentIntent ?: context.packageManager?.
            getLaunchIntentForPackage(packageName)
        intent?.apply {
            Log.d("debug-lamoda", packageName)
            putExtra(Dito.DITO_DEEP_LINK, deepLink)
            if(Dito.getHibridMode() != "ON") {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
        return intent
    }
}
