package br.com.dito.ditosdk.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import br.com.dito.ditosdk.Dito

class NotificationOpenedReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("OpenedReceiver", "onReceive")

        context?.let {
            if (!Dito.isInitialized()) {
                Dito.init(it.applicationContext, null)
            }
        }

        intent?.getStringExtra(Dito.DITO_NOTIFICATION_ID)?.let {
            Dito.notificationRead(it)
        }


        context?.let {
            getIntent(it)?.let {
                context.startActivity(intent)
            }
        }
    }


    private fun getIntent(context: Context): Intent? {
        val packageName = context.packageName
        val intent = Dito.options?.contentIntent ?: context.packageManager?.
                getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return intent
    }
}