package br.com.dito.ditosdk

import android.content.Intent
import android.support.annotation.IdRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Options (val contentIntent: Intent?, @IdRes val iconNotification: Int?)

data class Identify(@NonNull @Expose(serialize = false) val id: String) {
    var name: String? = null
    var email: String? = null
    var gender: String? = null
    var location: String? = null
    var birthday: String? = null
    var created_at: String? = null
    @Expose(serialize = false) var data: CustomData? = null
}

data class Event(@NonNull val action: String,  @Nullable val revenue: Double? = null) {
    @SerializedName("created_at") var createdAt: String? = null
    @Expose(serialize = false) var data: CustomData? = null
}

internal data class EventOff(val id: Int, val json: String, val retry: Int)

internal data class IdentifyOff(val id: String, val json: String,
                                val reference: String, val send: Boolean)