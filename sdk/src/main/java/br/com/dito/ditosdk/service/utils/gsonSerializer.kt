package br.com.dito.ditosdk.service.utils

import br.com.dito.ditosdk.Identify
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSerializer


fun customDataSerializer(): JsonSerializer<Map<String, Any>> {
    return JsonSerializer { src, typeOfSrc, context ->
        val json = JsonObject()
        src!!.forEach {
            if (it.value is String) json.addProperty(it.key, it.value as String)
            if (it.value is Int) json.addProperty(it.key, it.value as Int)
            if (it.value is Float) json.addProperty(it.key, it.value as Float)
            if (it.value is Boolean) json.addProperty(it.key, it.value as Boolean)
        }
        json
    }
}

fun identifySerializer(): JsonSerializer<Identify> {
    return JsonSerializer { src, typeOfSrc, context ->
        val gson = Gson()
        val data = gson.toJson(src.data!!.params)
        val identify = gson.toJsonTree(src, typeOfSrc).asJsonObject
        identify.addProperty("params", data)
        identify
    }
}