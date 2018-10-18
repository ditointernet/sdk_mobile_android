package br.com.dito.samplekotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.dito.ditosdk.CustomData
import br.com.dito.ditosdk.Dito
import br.com.dito.ditosdk.Event
import br.com.dito.ditosdk.Identify
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity() {

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickIdentify(view: View) {
        val data = CustomData()
        data.add("userId",  "uol")
        data.add("push", false)

        val identify = Identify("85496430259")
        identify.data = data

        Dito.identify(identify)
    }

    fun onClickEvent(view: View) {
        Dito.track(Event("comprou", 2.5))
    }

    fun onClickRegisterToken(view: View) {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            token = it?.token
            Dito.registerDevice(token!!)
        }
    }

    fun onClickDeleteToken(view: View) {
        token?.let {
            Dito.unregisterDevice(it)
        }
    }
}
