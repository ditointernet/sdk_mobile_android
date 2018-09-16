package br.com.dito

import android.app.Application
import br.com.dito.ditosdk.Dito

class DitoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Dito.init(this, null)
    }
}