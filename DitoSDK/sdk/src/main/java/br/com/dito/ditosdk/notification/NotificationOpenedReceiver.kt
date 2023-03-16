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


        Log.d("debug-lamoda", "Entrou no Recevier")
        context?.let {
            Log.d("debug-lamoda", "Verificou inicialização")
            if (!Dito.isInitialized()) {
                Log.d("debug-lamoda", "Entrou na inicialização")
                Dito.init(it.applicationContext, null)
                Log.d("debug-lamoda", "Inicializou")
            }
        }

        if (reference != null && notificationId != null) {
            Log.d("debug-lamoda", "Leu notificação")
            Dito.notificationRead(notificationId, reference)
        }

        context?.let {
            val intent = getIntent(it, deepLink)
            Log.d("debug-lamoda", "Carregou intent")
            try {
                context.startActivity(intent)
                Log.d("debug-lamoda", "abriu intent")
            } catch (e: Exception)  {Log.d("debug-lamoda", "Erro")}
        }
    }

    private fun getIntent(context: Context, deepLink: String?): Intent? {
        val packageName = context.packageName
        val intent = Dito.options?.contentIntent ?: context.packageManager?.
            getLaunchIntentForPackage(packageName)
        intent?.apply {
            Log.d("debug-lamoda", "aplicou extra")
            putExtra(Dito.DITO_DEEP_LINK, deepLink)
            if(Dito.getHibridMode() != "ON") {
                Log.d("debug-lamoda", "não é hibrid mode")
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
        return intent
    }
}
