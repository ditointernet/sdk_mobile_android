package br.com.dito.ditosdk.service

import br.com.dito.ditosdk.BuildConfig
import br.com.dito.ditosdk.CustomData
import br.com.dito.ditosdk.Identify
import br.com.dito.ditosdk.service.utils.customDataSerializer
import br.com.dito.ditosdk.service.utils.identifySerializer
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonSerializer
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


internal object RemoteService {

    private lateinit var retrofit: Retrofit
    private var httpClient: OkHttpClient
    private var baseUrl: String = ""

    private var gsonBuilder: GsonBuilder?

    init {

        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }

        httpClient = builder.build()

        gsonBuilder = GsonBuilder()
                .registerTypeAdapter(CustomData::class.java, customDataSerializer())
                .registerTypeAdapter(Identify::class.java, identifySerializer())
    }

    private fun createRetrofit() {

        val builder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder!!.create()))
                .client(httpClient)

        retrofit = builder.build()
    }


    fun loginApi(): LoginApi {
        if (baseUrl.isNullOrEmpty() || !baseUrl.contains("login")) {
            baseUrl = "https://login.plataformasocial.com.br"
            createRetrofit()
        }

        return retrofit.create(LoginApi::class.java)
    }
}