package br.com.dito.ditosdk.tracking

import android.support.annotation.Nullable
import android.util.Log
import br.com.dito.ditosdk.EventOff
import br.com.dito.ditosdk.IdentifyOff
import br.com.dito.ditosdk.NotificationReadOff
import br.com.dito.ditosdk.offline.DitoSqlHelper
import br.com.dito.ditosdk.service.utils.EventRequest
import br.com.dito.ditosdk.service.utils.NotificationOpenRequest
import br.com.dito.ditosdk.service.utils.SigunpRequest
import com.google.gson.Gson
import org.jetbrains.anko.db.*
import org.jetbrains.annotations.NotNull

internal class TrackerOffline(private var database: DitoSqlHelper) {

    private var gson: Gson = br.com.dito.ditosdk.service.utils.gson()

    fun identify(@NotNull sigunpRequest: SigunpRequest, @Nullable reference: String?, send: Boolean) {
        try {
            val json = gson.toJson(sigunpRequest)
            database.use {
                delete("Identify", "_id = {id}", "id" to sigunpRequest.userData.id)
                insert("Identify",
                        "_id" to sigunpRequest.userData.id,
                        "json" to json,
                        "reference" to reference,
                        "send" to send
                )
            }
        } catch(e: Exception) {
            Log.e("TrackerOffline", e.message, e)
        }
    }

    fun updateIdentify(id: String, reference: String, send: Boolean) {
        try {
            database.use {
                update("Identify", "reference" to reference, "send" to send)
                        .whereSimple("_id = ?", id)
            }
        } catch (e: Exception) {
            Log.e("TrackerOffline", e.message, e)
        }
    }

    fun event(@NotNull eventRequest: EventRequest) {
        try {
            val json = gson.toJson(eventRequest)
            database.use {
                insert("Event",
                        "json" to json)
            }
        } catch(e: Exception) {
            Log.e("TrackerOffline", e.message, e)
        }
    }

    fun delete(id: Int, tableName: String) {
        try {
            database.use {
                delete(tableName, "_id = {id}", "id" to id)
            }
        } catch(e: Exception) {
            Log.e("TrackerOffline", e.message, e)
        }
    }

    fun update(id: Int, retry: Int, tableName: String) {
        try {
            database.use {
                update(tableName, "retry" to retry)
                        .whereSimple("_id = ?", id.toString())
            }
        } catch (e: Exception) {
            Log.e("TrackerOffline", e.message, e)
        }
    }

    fun getAllEvents(): List<EventOff>? {
        return try {
            database.use {
                select("Event").parseList(classParser())
            }
        } catch (e: Exception) {
            null
        }
    }

    fun getAllNotificationRead(): List<NotificationReadOff>? {
        return try {
            database.use {
                select("NotificationRead").parseList(classParser())
            }
        } catch (e: Exception) {
            null
        }
    }

    fun getIdentify(): IdentifyOff? {
        try {
            return database.use {
                select("Identify")
                        .parseSingle(object: MapRowParser<IdentifyOff>{
                            override fun parseRow(columns: Map<String, Any?>): IdentifyOff {
                                val id = columns.getValue("_id").toString()
                                val json = columns.getValue("json").toString()
                                val reference = columns.getValue("reference").toString()
                                val send = columns.getValue("send").toString().toBoolean()
                                return IdentifyOff(id, json, reference, send)
                            }
                        })
            }
        } catch (e: Exception) {
            return null
        }
    }

    fun notificationRead(notificationOpenRequest: NotificationOpenRequest) {
        try {
            val json = gson.toJson(notificationOpenRequest)
            database.use {
                insert("NotificationRead",
                        "json" to json)
            }
        } catch(e: Exception) {
            Log.e("TrackerOffline", e.message, e)
        }
    }
}