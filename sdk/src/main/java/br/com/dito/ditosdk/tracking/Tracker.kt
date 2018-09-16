package br.com.dito.ditosdk.tracking

import android.util.Log
import br.com.dito.ditosdk.Identify
import br.com.dito.ditosdk.service.LoginApi
import br.com.dito.ditosdk.service.utils.SigunpRequest
import br.com.dito.ditosdk.utils.DitoSDKUtils
import kotlinx.coroutines.experimental.launch
import org.jetbrains.annotations.NotNull

internal class Tracker {

    private var apiKey: String
    private var apiSecret: String

    constructor(apiKey: String, apiSecret: String) {
        this.apiKey = apiKey
        this.apiSecret = DitoSDKUtils.SHA1(apiSecret)
    }

    fun identify(@NotNull identify: Identify, api: LoginApi) {
        launch {
            try {
                val params = SigunpRequest(apiKey, apiSecret, identify)
                val response = api.signup("portal", identify.id, params).await()
                if (response.isSuccessful) {

                } else {

                }
            } catch (e: Exception) {
                Log.e(Tracker::class.simpleName, e.message, e)
            }
        }
    }
}