package br.com.dito.ditosdk.tracking

import android.util.Log
import br.com.dito.ditosdk.Event
import br.com.dito.ditosdk.Identify
import br.com.dito.ditosdk.service.EventApi
import br.com.dito.ditosdk.service.LoginApi
import br.com.dito.ditosdk.service.utils.EventRequest
import br.com.dito.ditosdk.service.utils.SigunpRequest
import br.com.dito.ditosdk.utils.DitoSDKUtils
import kotlinx.coroutines.experimental.launch
import org.jetbrains.annotations.NotNull

internal class Tracker {

    private val TAG = "Tracker"

    private var apiKey: String
    private var apiSecret: String
    lateinit var id: String

    constructor(apiKey: String, apiSecret: String) {
        this.apiKey = apiKey
        this.apiSecret = DitoSDKUtils.SHA1(apiSecret)
    }

    fun identify(@NotNull identify: Identify, @NotNull api: LoginApi) {
        launch {
            try {
                //TODO: Salvar ID no banco
                id = identify.id
                val params = SigunpRequest(apiKey, apiSecret, identify)
                val response = api.signup("portal", identify.id, params).await()

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

    fun event(@NotNull event: Event, @NotNull api: EventApi ) {
        launch {
            try {
                val params = EventRequest(apiKey, apiSecret, event)
                val response = api.track(id, params).await()

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