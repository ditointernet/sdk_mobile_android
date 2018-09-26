package br.com.dito.ditosdk.tracking

import android.support.annotation.Nullable
import android.util.Log
import br.com.dito.ditosdk.CustomData
import br.com.dito.ditosdk.Event
import br.com.dito.ditosdk.EventOff
import br.com.dito.ditosdk.Identify
import br.com.dito.ditosdk.offline.DitoSqlHelper
import br.com.dito.ditosdk.service.utils.customDataSerializer
import br.com.dito.ditosdk.service.utils.eventSerializer
import br.com.dito.ditosdk.service.utils.identifySerializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.jetbrains.anko.db.*
import org.jetbrains.annotations.NotNull

internal class TrackerOffline {

    private val TAG = "TrackerOffline"

    private var database: DitoSqlHelper

    private var gson: Gson = GsonBuilder()
            .registerTypeAdapter(CustomData::class.java, customDataSerializer())
            .registerTypeAdapter(Identify::class.java, identifySerializer())
            .registerTypeAdapter(Event::class.java, eventSerializer())
            .create()

    constructor(database: DitoSqlHelper) {
        this.database = database
    }

    fun identify(@NotNull identify: Identify, @Nullable reference: String?, send: Boolean) {
        try {
            val json = gson.toJson(identify)
            database.use {
                delete("Identify")
                insert("Identify",
                        "_id" to identify.id,
                        "json" to json,
                        "reference" to reference,
                        "send" to send
                )
            }
        } catch(e: Exception) {
            Log.e(TAG, e.message, e)
        }
    }

    fun event(@NotNull event: Event) {
        try {
            val json = gson.toJson(event)
            database.use {
                insert("Event",
                        "json" to json)
            }
        } catch(e: Exception) {
            Log.e(TAG, e.message, e)
        }
    }

    fun deleteEvent(id: Int) {
        try {
            database.use {
                delete("Event", "_id = {id}", "id" to id)
            }
        } catch(e: Exception) {
            Log.e(TAG, e.message, e)
        }
    }

    fun updateEvent(id: Int, retry: Int) {
        try {
            database.use {
                update("Event", "retry" to retry)
                        .whereSimple("_id = ?", id.toString())
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
        }
    }

    fun getAllEvents(): List<EventOff> {
        return database.use {
            select("Event").parseList(classParser())
        }
    }
}