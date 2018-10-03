package br.com.dito.ditosdk.service

import br.com.dito.ditosdk.service.utils.SigunpRequest
import com.google.gson.JsonObject
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

internal interface LoginApi {
    @POST("/users/{network}/{id}/signup")
    fun signup(@Path("network") network: String,
               @Path("id") id: String, @Body data: SigunpRequest)
            : Deferred<Response<JsonObject>>

    @POST("/users/{network}/{id}/signup")
    fun signup(@Path("network") network: String,
               @Path("id") id: String, @Body data: JsonObject)
            : Deferred<Response<JsonObject>>
}