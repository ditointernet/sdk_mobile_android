package br.com.dito.ditosdk

import android.content.Intent
import android.support.annotation.IdRes
import android.support.annotation.NonNull

data class Options (val contentIntent: Intent?, @IdRes val iconNotification: Int?)

data class Identify(@NonNull @Transient val id: String) {
    var name: String? = null
    var email: String? = null
    var gender: String? = null
    var location: String? = null
    var birthday: String? = null
    var created_at: String? = null
    @Transient var data: CustomData? = null
}