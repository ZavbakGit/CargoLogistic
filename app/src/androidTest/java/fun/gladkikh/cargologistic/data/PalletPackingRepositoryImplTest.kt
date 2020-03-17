package `fun`.gladkikh.cargologistic.data

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.domain.repository.PalletPackingRepository
import `fun`.gladkikh.cargologistic.domain.repository.SettingsRepository
import `fun`.gladkikh.cargologistic.preferences.PreferencesImpl
import `fun`.gladkikh.cargologistic.preferences.SharedPrefsManager
import androidx.preference.PreferenceManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PalletPackingRepositoryImplTest {

    lateinit var gson: Gson
    lateinit var preef: PreferencesRequest
    lateinit var settingsRepository: SettingsRepository
    lateinit var repository: PalletPackingRepository


    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun init() {
        gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

        preef = PreferencesImpl(
            prefsManager = SharedPrefsManager(
                prefs = PreferenceManager.getDefaultSharedPreferences(appContext),
                gson = gson
            )

        )

        settingsRepository = SettingsRepositoryImpl(preef)

        settingsRepository.getSettings().either({

        }, {
            App.initRequestRemote(it)
        })

        repository = PalletPackingRepositoryImpl(
            preef,
            gson
        )

    }


    @Test
    fun getOrderByBarcode() {
        repository.getOrderByBarcode("111").either({
            assert(false)
        }, {
            println("result: $it")
            assertEquals("ЗА-00327", it.number)
        })
    }

    @Test
    fun getPalletByBarcode() {
        repository.getPalletByBarcode("111").either({
            assert(false)
        }, {
            println("result: $it")
            assertEquals("3256", it.number)
        })
    }
}