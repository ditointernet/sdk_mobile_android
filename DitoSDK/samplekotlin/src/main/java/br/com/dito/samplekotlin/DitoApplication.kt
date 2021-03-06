package br.com.dito.samplekotlin

import android.app.Application
import br.com.dito.ditosdk.Dito
import br.com.dito.ditosdk.Options
import com.facebook.stetho.Stetho


class DitoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
        val options = Options()
        options.debug = true
        Dito.init(this, options)
    }
}