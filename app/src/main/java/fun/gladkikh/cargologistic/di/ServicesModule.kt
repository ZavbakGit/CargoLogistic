package `fun`.gladkikh.cargologistic.di

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.data.PreferencesRequest
import `fun`.gladkikh.cargologistic.preferences.PreferencesImpl
import `fun`.gladkikh.cargologistic.preferences.SharedPrefsManager
import `fun`.gladkikh.cargologistic.data.RemoteRequest
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
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }


    @Provides
    @Singleton
    fun providePreferences(sharedPrefsManager: SharedPrefsManager): PreferencesRequest {
        return PreferencesImpl(sharedPrefsManager)
    }

    @Provides
    @Singleton
    fun provideRequestRemote(): RemoteRequest? {
        return App.requestRemote
    }
}