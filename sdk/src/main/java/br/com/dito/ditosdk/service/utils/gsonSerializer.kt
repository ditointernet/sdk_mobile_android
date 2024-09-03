package br.com.dito.ditosdk.service.utils

import br.com.dito.ditosdk.CustomData
import br.com.dito.ditosdk.Event
import br.com.dito.ditosdk.Identify
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonSerializer

internal fun customDataSerializer(): JsonSerializer<Map<String, Any>> {
    return JsonSerializer { src, _, _ ->
        val json = JsonObject()
        src!!.forEach {
            if (it.value is String) json.addProperty(it.key, it.value as String)
            if (it.value is Int) json.addProperty(it.key, it.value as Int)
            if (it.value is Double) json.addProperty(it.key, it.value as Double)
            if (it.value is Boolean) json.addProperty(it.key, it.value as Boolean)
        }
        json
    }
}

internal fun identifySerializer(): JsonSerializer<Identify> {
    return JsonSerializer { src, typeOfSrc, _ ->
        val gson = Gson()
        val data = gson.toJson(src.data?.params)
        val identify = gson.toJsonTree(src, typeOfSrc).asJsonObject
        identify.addProperty("data", data)
        identify
    }
}


internal fun eventSerializer(): JsonSerializer<Event> {
    return JsonSerializer { src, typeOfSrc, _ ->
        val gson = Gson()
        val data = gson.toJson(src.data?.params)
        val event = gson.toJsonTree(src, typeOfSrc).asJsonObject
        event.addProperty("data", data)
        event
    }
}

internal fun eventRequestSerializer(): JsonSerializer<EventRequest> {
    return JsonSerializer { src, typeOfSrc, _ ->
        val gson = GsonBuilder().registerTypeAdapter(Event::class.java, eventSerializer()).create()
        val event = gson.toJson(src.event)
        val request = gson.toJsonTree(src, typeOfSrc).asJsonObject
        request.addProperty("event", event)
        request
    }
}

internal fun gson(): Gson {
    return GsonBuilder()
            .registerTypeAdapter(CustomData::class.java, customDataSerializer())
            .registerTypeAdapter(Identify::class.java, identifySerializer())
            .registerTypeAdapter(Event::class.java, eventSerializer())
            .registerTypeAdapter(EventRequest::class.java, eventRequestSerializer())
            .create()
}