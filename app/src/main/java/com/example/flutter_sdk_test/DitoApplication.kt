package com.example.flutter_sdk_test

import android.app.Application
import br.com.dito.ditosdk.Dito
import br.com.dito.ditosdk.Options

class DitoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val options = Options()
        options.debug = true
        Dito.init(this, options)
    }
}