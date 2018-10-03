package br.com.dito.ditosdk.tracking

import android.support.annotation.NonNull
import android.util.Log
import br.com.dito.ditosdk.Event
import br.com.dito.ditosdk.Identify
import br.com.dito.ditosdk.service.EventApi
import br.com.dito.ditosdk.service.LoginApi
import br.com.dito.ditosdk.service.NotificationApi
import br.com.dito.ditosdk.service.utils.*
import br.com.dito.ditosdk.utils.DitoSDKUtils
import com.google.gson.JsonObject
import kotlinx.coroutines.experimental.launch

internal class Tracker {

    private val TAG = "Tracker"

    var apiKey: String
    var apiSecret: String
    private var trackerOffline: TrackerOffline

    lateinit var id: String
    lateinit var reference: String

    constructor(apiKey: String, apiSecret: String, trackerOffline: TrackerOffline) {
        this.apiKey = apiKey
        this.apiSecret = DitoSDKUtils.SHA1(apiSecret)
        this.trackerOffline = trackerOffline
        loadIdentify()
    }

    private fun loadIdentify() {
        launch {
            val identify = trackerOffline.getIdentify()
            identify?.let {
                id = it.id
                reference = it.reference
            }
        }
    }

    fun identify(@NonNull identify: Identify, @NonNull api: LoginApi) {
        launch {
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
        launch {
            val params = EventRequest(apiKey, apiSecret, event)
            try {
                val response = api.track(id, params).await()

                if (!response.isSuccessful) {
                    trackerOffline.event(params)
                }
            } catch (e: Exception) {
                trackerOffline.event(params)
            }
        }
    }

    fun registerToken(@NonNull token: String, @NonNull api: NotificationApi) {
        launch {
            try {
                val params = TokenRequest(apiKey, apiSecret, token)
                val response = api.add(id, params).await()

                if (!response.isSuccessful) {
                    Log.d(TAG, response.errorBody().toString())
                    //TODO: verificar error e salvar no banco
                }
            } catch (e: Exception) {
                //TODO: verificar error e salvar no banco
                Log.e(TAG, e.message, e)
            }
        }
    }

    fun unregisterToken(@NonNull token: String, @NonNull api: NotificationApi) {
        launch {
            try {
                val params = TokenRequest(apiKey, apiSecret, token)
                val response = api.disable(id, params).await()

                if (!response.isSuccessful) {
                    Log.d(TAG, response.errorBody().toString())
                    //TODO: verificar error e salvar no banco
                }
            } catch (e: Exception) {
                //TODO: verificar error e salvar no banco
                Log.e(TAG, e.message, e)
            }
        }
    }

    fun notificationRead(@NonNull notificationId: String, @NonNull api: NotificationApi) {
        launch {
            val data = JsonObject()
            data.addProperty("identifier", id)
            data.addProperty("reference", reference)

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