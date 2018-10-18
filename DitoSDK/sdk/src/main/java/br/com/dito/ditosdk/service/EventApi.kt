package br.com.dito.ditosdk.service

import br.com.dito.ditosdk.service.utils.EventRequest
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

internal interface EventApi {
    @POST("/users/{id}")
    fun track(@Path("id") id: String, @Body data: EventRequest)
            : Deferred<Response<JsonObject>>

    @POST("/users/{id}")
    fun track(@Path("id") id: String, @Body data: JsonObject)
            : Deferred<Response<JsonObject>>
}