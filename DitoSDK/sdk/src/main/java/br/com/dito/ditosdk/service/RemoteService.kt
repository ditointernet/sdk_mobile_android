package br.com.dito.ditosdk.service

import br.com.dito.ditosdk.BuildConfig
import br.com.dito.ditosdk.service.utils.gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


internal object RemoteService {

    private lateinit var retrofit: Retrofit
    private var httpClient: OkHttpClient
    private var baseUrl: String = ""

    init {

        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }

        httpClient = builder.build()
    }

    private fun createRetrofit() {

        val builder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .client(httpClient)

        retrofit = builder.build()
    }


    fun loginApi(): LoginApi {
        if (baseUrl.isEmpty() || !baseUrl.contains("login")) {
            baseUrl = "https://login.plataformasocial.com.br"
            createRetrofit()
        }

        return retrofit.create(LoginApi::class.java)
    }

    fun eventApi(): EventApi {
        if (baseUrl.isEmpty() || !baseUrl.contains("events")) {
            baseUrl = "https://events.plataformasocial.com.br"
            createRetrofit()
        }

        return retrofit.create(EventApi::class.java)
    }

    fun notificationApi(): NotificationApi {
        if (baseUrl.isEmpty() || !baseUrl.contains("notification")) {
            baseUrl = "https://notification.plataformasocial.com.br"
            createRetrofit()
        }

        return retrofit.create(NotificationApi::class.java)
    }
}