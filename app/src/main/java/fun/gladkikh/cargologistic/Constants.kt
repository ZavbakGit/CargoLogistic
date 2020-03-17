package `fun`.gladkikh.cargologistic

import android.annotation.SuppressLint
import android.provider.Settings

@SuppressLint("HardwareIds")
object Constants {
    //const val BASE_URL = "http://172.31.255.168/rmmt/hs/api/"
    //const val BASE_URL = "http://172.31.255.150/rmmt/hs/api/"

    const val APP_VERSION = BuildConfig.VERSION_NAME
    val UID by lazy {
        App.appContext()?.let {
            Settings.Secure.getString(it.contentResolver, Settings.Secure.ANDROID_ID)
        }
    }
    val OS_VERSION by lazy { android.os.Build.VERSION.SDK_INT.toString() }

    const val COMMAND_OPEN_MAIN = 1
    const val IS_TEST_BUILD = true
    const val DATABASE_NAME = "cargologistic-db"

}

