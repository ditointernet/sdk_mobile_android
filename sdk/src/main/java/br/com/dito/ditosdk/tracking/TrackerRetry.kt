package br.com.dito.ditosdk.tracking

import br.com.dito.ditosdk.Event
import br.com.dito.ditosdk.EventOff
import br.com.dito.ditosdk.Identify
import br.com.dito.ditosdk.service.RemoteService
import br.com.dito.ditosdk.service.utils.EventRequest
import kotlinx.coroutines.experimental.launch

internal class TrackerRetry {

    private val TAG = "TrackerRetry"

    private var tracker: Tracker
    private var trackerOffline: TrackerOffline
    private var retry: Int
    private val gson = br.com.dito.ditosdk.service.utils.gson()
    private val apiEvent = RemoteService.eventApi()

    constructor(tracker: Tracker, trackerOffline: TrackerOffline, retry: Int = 5) {
        this.tracker = tracker
        this.trackerOffline = trackerOffline
        this.retry = retry
    }

    fun uploadEvents() {
        checkIdentify()
        checkEvent()
    }

    private fun checkIdentify() {
        launch {
            val identifyOff = trackerOffline.getIdentify()
            identifyOff?.let {
                if (!it.send) {
                    val value = gson.fromJson(it.json, Identify::class.java)
                    tracker.identify(value, RemoteService.loginApi())
                }
            }
        }
    }

    private fun checkEvent() {
        launch {
            val events = trackerOffline.getAllEvents()
            events?.forEach {
                if (it.retry == retry) {
                    trackerOffline.deleteEvent(it.id)
                }
                else {
                    sendEvent(it, tracker.id)
                }
            }
        }
    }

    private suspend fun sendEvent(eventOff: EventOff, id: String) {
        val event = gson.fromJson<Event>(eventOff.json, Event::class.java)

        try {
            val params = EventRequest(tracker.apiKey, tracker.apiSecret, event)
            val response = apiEvent.track(id, params).await()

            if (!response.isSuccessful) {
                trackerOffline.updateEvent(eventOff.id, (eventOff.retry + 1))
            } else {
                trackerOffline.deleteEvent(eventOff.id)
            }
        } catch (e: Exception) {
            trackerOffline.updateEvent(eventOff.id, (eventOff.retry + 1))
        }
    }


}