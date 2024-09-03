package br.com.dito.ditosdk.tracking

import android.content.Context
import android.util.Log
import androidx.room.Room
import br.com.dito.ditosdk.EventOff
import br.com.dito.ditosdk.IdentifyOff
import br.com.dito.ditosdk.NotificationReadOff
import br.com.dito.ditosdk.offline.DitoSqlHelper
import br.com.dito.ditosdk.offline.EventOffline
import br.com.dito.ditosdk.offline.IdentifyOffline
import br.com.dito.ditosdk.offline.NotificationOffline
import br.com.dito.ditosdk.service.utils.EventRequest
import br.com.dito.ditosdk.service.utils.NotificationOpenRequest
import br.com.dito.ditosdk.service.utils.SigunpRequest
import com.google.gson.Gson

internal class TrackerOffline(private var context: Context) {
    private var gson: Gson = br.com.dito.ditosdk.service.utils.gson()
    private val database = Room.databaseBuilder(
        context,
        DitoSqlHelper::class.java, "dito-offline"
    ).build()


    fun identify(sigunpRequest: SigunpRequest, reference: String?, send: Boolean) {
        try {
            val json = gson.toJson(sigunpRequest)
            database.identifyDao().insert(
                IdentifyOffline(
                    _id = sigunpRequest.userData.id,
                    json = json,
                    reference = reference,
                    send = send
                )
            )
        } catch (e: Exception) {
            Log.e("TrackerOffline", e.message, e)
        }
    }

    fun updateIdentify(id: String, send: Boolean) {
        try {
            database.identifyDao().update(
                send,
                id.toInt(),
            )
        } catch (e: Exception) {
            Log.e("TrackerOffline", e.message, e)
        }
    }

    fun event(eventRequest: EventRequest) {
        try {
            val json = gson.toJson(eventRequest)

            database.eventDao().insert(
                EventOffline(
                    json = json,
                    retry = 0
                )
            )
        } catch (e: Exception) {
            Log.e("TrackerOffline", e.message, e)
        }
    }

    fun delete(id: Int, tableName: String) {
        try {
            when (tableName) {
                "Event" -> database.eventDao().delete(id)
                "NotificationRead" -> database.notificationDao().delete(id)
            }
        } catch (e: Exception) {
            Log.e("TrackerOffline", e.message, e)
        }
    }

    fun update(id: Int, retry: Int, tableName: String) {
        try {

            when (tableName) {
                "Event" -> database.eventDao().update(id, retry)
                "NotificationRead" -> database.notificationDao().update(id, retry)
            }
        } catch (e: Exception) {
            Log.e("TrackerOffline", e.message, e)
        }
    }

    fun getAllEvents(): List<EventOff>? {
        return try {
            database.eventDao().getAll().map {
                gson.fromJson(it.json, EventOff::class.java)
            }
        } catch (e: Exception) {
            null
        }
    }

    fun getAllNotificationRead(): List<NotificationReadOff>? {
        return try {
            database.notificationDao().getAll().map {
                gson.fromJson(it.json, NotificationReadOff::class.java)
            }
        } catch (e: Exception) {
            null
        }
    }

    fun getIdentify(): IdentifyOff? {
        try {
            val identify = database.identifyDao().getAll().first()

            return IdentifyOff(
                identify._id.toString(),
                identify.json.toString(),
                identify.reference.toString(),
                identify.send
            )
        } catch (e: Exception) {
            return null
        }
    }

    fun notificationRead(notificationOpenRequest: NotificationOpenRequest) {
        try {
            val json = gson.toJson(notificationOpenRequest)

            database.notificationDao().insert(
                NotificationOffline(
                    json = json,
                    retry = 0
                )
            )

        } catch (e: Exception) {
            Log.e("TrackerOffline", e.message, e)
        }
    }
}