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
import kotlinx.coroutines.experimental.launch

internal class Tracker {

    private val TAG = "Tracker"

    private var apiKey: String
    private var apiSecret: String
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
            try {
                id = identify.id
                val params = SigunpRequest(apiKey, apiSecret, identify)
                val response = api.signup("portal", identify.id, params).await()

                if (!response.isSuccessful) {
                    trackerOffline.identify(identify, null, false)
                } else {
                    reference = response.body()!!
                            .getAsJsonObject("data").get("reference").asString
                    trackerOffline.identify(identify, reference, true)
                }

            } catch (e: Exception) {
                trackerOffline.identify(identify, null, false)
            }
        }
    }

    fun event(@NonNull event: Event, @NonNull api: EventApi ) {
        launch {
            try {
                val params = EventRequest(apiKey, apiSecret, event)
                val response = api.track(id, params).await()

                if (!response.isSuccessful) {
                    trackerOffline.event(event)
                }
            } catch (e: Exception) {
                trackerOffline.event(event)
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
            try {
                val data = JsonObject()
                data.addProperty("identifier", id)
                data.addProperty("reference", reference)

                val params = NotificationOpenRequest(apiKey, apiSecret, data.toString())

                val response = api.open(notificationId, params).await()

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
}