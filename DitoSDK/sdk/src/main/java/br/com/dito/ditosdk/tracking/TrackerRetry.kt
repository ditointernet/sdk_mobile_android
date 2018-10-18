package br.com.dito.ditosdk.tracking

import br.com.dito.ditosdk.EventOff
import br.com.dito.ditosdk.NotificationReadOff
import br.com.dito.ditosdk.service.RemoteService
import com.google.gson.JsonObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal class TrackerRetry(private var tracker: Tracker, private var trackerOffline: TrackerOffline, private var retry: Int = 5) {

    private val gson = br.com.dito.ditosdk.service.utils.gson()
    private val apiEvent = RemoteService.eventApi()
    private val apiNotification = RemoteService.notificationApi()

    fun uploadEvents() {
        checkIdentify()
        checkEvent()
        checkNotificationRead()
    }

    private fun checkIdentify() {
        GlobalScope.launch {
            val identifyOff = trackerOffline.getIdentify()
            identifyOff?.let {
                if (!it.send) {
                    val value = gson.fromJson(it.json, JsonObject::class.java)
                    val api = RemoteService.loginApi()
                    val response = api.signup("portal", identifyOff.id, value).await()
                    if (response.isSuccessful) {
                        val reference = response.body()!!
                                .getAsJsonObject("data").get("reference").asString
                        trackerOffline.updateIdentify(identifyOff.id, reference, true)
                    }
                }
            }
        }
    }

    private fun checkEvent() {
        GlobalScope.launch {
            val events = trackerOffline.getAllEvents()
            events?.forEach {
                if (it.retry == retry) {
                    trackerOffline.delete(it.id, "Event")
                }
                else {
                    sendEvent(it, tracker.id)
                }
            }
        }
    }

    private suspend fun sendEvent(eventOff: EventOff, id: String) {
        try {
            val params = gson.fromJson(eventOff.json, JsonObject::class.java)
            val response = apiEvent.track(id, params).await()
            if (!response.isSuccessful) {
                trackerOffline.update(eventOff.id, (eventOff.retry + 1), "Event")
            } else {
                trackerOffline.delete(eventOff.id, "Event")
            }
        } catch (e: Exception) {
            trackerOffline.update(eventOff.id, (eventOff.retry + 1), "Event")
        }
    }


    private fun checkNotificationRead() {
        GlobalScope.launch {
            val notifications = trackerOffline.getAllNotificationRead()
            notifications?.forEach {
                if (it.retry == retry) {
                    trackerOffline.delete(it.id, "NotificationRead")
                }
                else {
                    sendNotificationRead(it, tracker.id)
                }
            }
        }
    }

    private suspend fun sendNotificationRead(notificationReadOff: NotificationReadOff, id: String) {
        try {
            val params = gson.fromJson(notificationReadOff.json, JsonObject::class.java)
            val response = apiNotification.open(id, params).await()
            if (!response.isSuccessful) {
                trackerOffline.update(notificationReadOff.id, (notificationReadOff.retry + 1), "NotificationRead")
            } else {
                trackerOffline.delete(notificationReadOff.id, "NotificationRead")
            }
        } catch (e: Exception) {
            trackerOffline.update(notificationReadOff.id, (notificationReadOff.retry + 1), "NotificationRead")
        }
    }


}