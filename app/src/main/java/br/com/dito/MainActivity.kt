package br.com.dito

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.dito.ditosdk.CustomData
import br.com.dito.ditosdk.Dito
import br.com.dito.ditosdk.Identify

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = CustomData()
        data.add("userId",  "uol")
        data.add("push", false)
        val identify = Identify("85496430259")
        identify.data = data
        Dito.identify(identify)
    }
}
