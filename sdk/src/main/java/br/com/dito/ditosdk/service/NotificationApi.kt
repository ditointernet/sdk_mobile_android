package br.com.dito.ditosdk.service

import br.com.dito.ditosdk.service.utils.NotificationOpenRequest
import br.com.dito.ditosdk.service.utils.TokenRequest
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

internal interface NotificationApi {
    @POST("/users/{id}/mobile-tokens/")
    fun add(@Path("id") id: String, @Body data: TokenRequest)
            : Deferred<Response<JsonObject>>


    @POST("/users/{id}/mobile-tokens/disable/")
    fun disable(@Path("id") id: String, @Body data: TokenRequest)
            : Deferred<Response<JsonObject>>

    @POST("/notifications/{id}/open/")
    fun open(@Path("id") id:String, @Body data: NotificationOpenRequest)
            : Deferred<Response<JsonObject>>

    @POST("/notifications/{id}/open/")
    fun open(@Path("id") id:String, @Body data: JsonObject)
            : Deferred<Response<JsonObject>>
}