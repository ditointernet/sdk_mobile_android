package br.com.dito.ditosdk.service

import br.com.dito.ditosdk.service.utils.SigunpRequest
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import retrofit2.http.*

internal interface DitoAPI {

    @POST("/users/{network}/{id}/signup")
    fun signup(@Path("network") network: String,
               @Path("id") id: String, @Body data: SigunpRequest)
            : Deferred<Response<SigunpResponse>>
}