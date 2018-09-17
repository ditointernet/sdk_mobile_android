package br.com.dito.ditosdk.service

import br.com.dito.ditosdk.service.utils.AddTokenRequest
import br.com.dito.ditosdk.service.utils.EventRequest
import br.com.dito.ditosdk.service.utils.SigunpRequest
import com.google.gson.JsonObject
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

internal interface NotificationApi {
    @POST("/users/{id}/mobile-tokens/")
    fun track(@Path("id") id: String, @Body data: AddTokenRequest)
            : Deferred<Response<JsonObject>>
}