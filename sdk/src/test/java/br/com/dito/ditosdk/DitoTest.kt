package br.com.dito.ditosdk

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.assertNotNull
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.reflect.Whitebox

@RunWith(PowerMockRunner::class)
@PrepareForTest(Context::class, ApplicationInfo::class, PackageManager::class )
class DitoTest {

    private lateinit var context: Context

    private lateinit var applicationInfo: ApplicationInfo

    private lateinit var  packageManager: PackageManager


    @Before
    fun setUp() {
        context = mock()
        packageManager = mock()
        applicationInfo = mock()

        whenever(context.packageManager).thenReturn(packageManager)
        whenever(context.packageName).thenReturn("br.com.dito.ditosdk")
        whenever(packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA))
                .thenReturn(applicationInfo)

    }


    @Test
    fun readCredentialsFromManifest() {
        val bundle = mock<Bundle>()
        whenever(bundle.getString("br.com.dito.API_KEY")).thenReturn("")
        whenever(bundle.getString("br.com.dito.API_SECRET")).thenReturn("")

        Whitebox.setInternalState(applicationInfo, "metaData", bundle)

        try {
            Dito.init(context, null)
            fail("Expected an RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            MatcherAssert.assertThat(e.message, CoreMatchers.containsString("AndroidManifest"))
        }
    }

    @Test
    fun validadeOption() {
        initContext()
        Dito.init(context, Options(null, null))
        assertNotNull(Dito.options)
    }


    private fun initContext() {
        val bundle = mock<Bundle>()
        whenever(bundle.getString("br.com.dito.API_KEY")).thenReturn("MjAxNS0wMy0zMSAxMToxOToyNCAtMDMwME1vYmlsZSBTREtzMTI4")
        whenever(bundle.getString("br.com.dito.API_SECRET")).thenReturn("vutpDmAIUdMfVQgcGiGanCun4opBVRUJoqIIzyGi")
        Whitebox.setInternalState(applicationInfo, "metaData", bundle)
    }
}