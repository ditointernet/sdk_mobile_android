package com.example.flutter_sdk_test

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.dito.ditosdk.CustomData
import br.com.dito.ditosdk.Dito
import br.com.dito.ditosdk.Event
import br.com.dito.ditosdk.Identify
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private var token: String? = null
    private val pushNotificationPermissionLauncher
        get() = registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pushNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
    }

    fun onClickIdentify(view: View) {
        val data = CustomData()
        data.add("userId", "uol")
        data.add("push", false)

        val identify = Identify("44444444444")
        identify.data = data
        identify.name = "Usuário de teste Android"
        identify.email = "teste+android@dito.com.br"

        Dito.identify(identify) { registerToken() }
    }

    private fun registerToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Dito", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            token = task.result
            Dito.registerDevice(token!!)
            Log.d("identify", "success on registering token")
        })
    }

    fun onClickEvent(view: View) {
        Dito.track(Event("comprou", 2.5))
    }

    fun onClickRegisterToken(view: View) {
        registerToken()
    }

    fun onClickDeleteToken(view: View) {
        token?.let {
            Dito.unregisterDevice(it)
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun AppPreview() {

}