package br.com.dito.ditosdk

import android.support.annotation.NonNull

class CustomData {

    val params: MutableMap<String, Any>  = LinkedHashMap()

    fun add(@NonNull key: String, @NonNull value: String) {
        params[key] = value
    }

    fun add(@NonNull key: String, @NonNull value: Int) {
        params[key] = value
    }

    fun add(@NonNull key: String, @NonNull value: Float) {
        params[key] = value
    }

    fun add(@NonNull key: String, @NonNull value: Boolean) {
        params[key] = value
    }
}