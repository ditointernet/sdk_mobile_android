package br.com.dito.ditosdk

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DitoInstrumentedTest {

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val options = Options(1)
        options.debug = true
        Dito.init(appContext, options)
    }

    @Test
    fun testIdentify() {
        runBlocking { Dito.identify(Identify("tannusesquerdo")) }
    }

    @Test
    fun testIdentifyWithCustomData() {
        val data = CustomData()
        data.add("idade", 31)
        data.add("topic", "praia")

        val identify = Identify("tannusesquerdo")
        identify.data = data

        runBlocking { Dito.identify(identify) }
    }


    @Test
    fun testEvent() {
        runBlocking { Dito.identify(Identify("tannusesquerdo")) }
        runBlocking { Dito.track(Event("acessou_home")) }
    }

    @Test
    fun testEventWithRevenue() {
        runBlocking { Dito.identify(Identify("tannusesquerdo")) }
        runBlocking { Dito.track(Event("acessou_checkout", 350.22)) }
    }

    @Test
    fun testEventWithCustomData() {
        runBlocking { Dito.identify(Identify("tannusesquerdo")) }
        val data = CustomData()
        data.add("quantidade", 6)
        data.add("produto", "cerveja")

        val event = Event("comprou", 60.0)
        event.data = data

        runBlocking { Dito.track(event) }
    }



}