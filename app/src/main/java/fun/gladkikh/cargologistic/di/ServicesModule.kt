package `fun`.gladkikh.cargologistic.di
import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.data.Preferences
import `fun`.gladkikh.cargologistic.preferences.PreferencesImpl
import `fun`.gladkikh.cargologistic.preferences.SharedPrefsManager
import `fun`.gladkikh.cargologistic.remote.RequestRemote
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServicesModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }


    @Provides
    @Singleton
    fun providePreferences(sharedPrefsManager: SharedPrefsManager): Preferences {
        return PreferencesImpl(sharedPrefsManager)
    }

    @Provides
    @Singleton
    fun provideRequestRemote(): RequestRemote? {
        return App.requestRemote
    }
}