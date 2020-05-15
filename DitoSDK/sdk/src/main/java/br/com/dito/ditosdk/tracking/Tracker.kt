package br.com.dito.ditosdk.tracking

import android.support.annotation.NonNull
import android.util.Log
import br.com.dito.ditosdk.Event
import br.com.dito.ditosdk.Identify
import br.com.dito.ditosdk.service.EventApi
import br.com.dito.ditosdk.service.LoginApi
import br.com.dito.ditosdk.service.NotificationApi
import br.com.dito.ditosdk.service.utils.EventRequest
import br.com.dito.ditosdk.service.utils.NotificationOpenRequest
import br.com.dito.ditosdk.service.utils.SigunpRequest
import br.com.dito.ditosdk.service.utils.TokenRequest
import br.com.dito.ditosdk.utils.DitoSDKUtils
import com.google.gson.JsonObject
import kotlinx.coroutines.*

internal class Tracker(private var apiKey: String, apiSecret: String, private var trackerOffline: TrackerOffline) {

    private var apiSecret: String = DitoSDKUtils.SHA1(apiSecret)

    lateinit var id: String
    lateinit var reference: String

    init {
        loadIdentify()
    }

    private fun loadIdentify() {
        GlobalScope.launch(Dispatchers.IO) {
            val identify = trackerOffline.getIdentify()
            identify?.let {
                id = it.id
                reference = it.reference
            }
        }
    }

    fun identify(@NonNull identify: Identify, @NonNull api: LoginApi) {
        GlobalScope.launch(Dispatchers.IO) {
            id = identify.id
            val params = SigunpRequest(apiKey, apiSecret, identify)
            try {
                val response = api.signup("portal", identify.id, params).await()

                if (!response.isSuccessful) {
                    trackerOffline.identify(params, null, false)
                } else {
                    reference = response.body()!!
                            .getAsJsonObject("data").get("reference").asString
                    trackerOffline.identify(params, reference, true)
                }

            } catch (e: Exception) {
                trackerOffline.identify(params, null, false)
            }
        }
    }

    fun event(@NonNull event: Event, @NonNull api: EventApi ) {
        GlobalScope.launch(Dispatchers.IO) {
            val params = EventRequest(apiKey, apiSecret, event)
            try {
                val response = api.track(id, params).await()

                if (!response.isSuccessful) {
                    trackerOffline.event(params)
                }
            } catch (e: Exception) {
                if (e is UninitializedPropertyAccessException) {
                    Log.e("Tracker", "Antes de enviar um evento é preciso identificar o usuário.")
                }
                trackerOffline.event(params)
            }
        }
    }

    fun registerToken(@NonNull token: String, @NonNull api: NotificationApi) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val params = TokenRequest(apiKey, apiSecret, token)
                val response = api.add(id, params).await()

                if (!response.isSuccessful) {
                    Log.d("Tracker", response.errorBody().toString())
                }
            } catch (e: Exception) {
                if (e is UninitializedPropertyAccessException) {
                    Log.e("Tracker", "Antes de registrar o token é preciso identificar o usuário.")
                }
            }
        }
    }

    fun unregisterToken(@NonNull token: String, @NonNull api: NotificationApi) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val params = TokenRequest(apiKey, apiSecret, token)
                val response = api.disable(id, params).await()

                if (!response.isSuccessful) {
                    Log.d("Tracker", response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.e("Tracker", e.message, e)
            }
        }
    }

    fun notificationRead(@NonNull notificationId: String, @NonNull api: NotificationApi, notificationReference: String) {
        GlobalScope.launch(Dispatchers.IO) {
            if (notificationReference != null && notificationReference != "") {
                val data = JsonObject()
                data.addProperty("identifier", notificationReference.substring(5))
                data.addProperty("reference", notificationReference)

                val params = NotificationOpenRequest(apiKey, apiSecret, data.toString())

                try {
                    val response = api.open(notificationId, params).await()

                    if (!response.isSuccessful) {
                        trackerOffline.notificationRead(params)
                    }

                } catch (e: Exception) {
                    trackerOffline.notificationRead(params)
                }
            }
        }
    }
}
