package br.com.dito.ditosdk

import android.content.Intent
import android.support.annotation.IdRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import br.com.dito.ditosdk.utils.formatToISO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class Options (val retry: Int = 5) {
    var contentIntent: Intent? = null
    @IdRes var iconNotification: Int? = null
    var debug: Boolean = false
}

data class Identify(@NonNull @Expose(serialize = false) val id: String) {
    var name: String? = null
    var email: String? = null
    var gender: String? = null
    var location: String? = null
    var birthday: String? = null
    @SerializedName("created_at")
    var createdAt: String? = Date().formatToISO()
    @Expose(serialize = false) var data: CustomData? = null
}

data class Event(@NonNull val action: String,  @Nullable val revenue: Double? = null) {
    @SerializedName("created_at") var createdAt: String? = Date().formatToISO()
    @Expose(serialize = false) var data: CustomData? = null
}

internal data class EventOff(val id: Int, val json: String, val retry: Int)

internal data class IdentifyOff(val id: String, val json: String,
                                val reference: String, val send: Boolean)

internal data class NotificationReadOff(val id: Int, val json: String, val retry: Int)