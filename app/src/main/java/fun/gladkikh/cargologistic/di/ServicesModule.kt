package `fun`.gladkikh.cargologistic.di

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.data.DataBaseRequest
import `fun`.gladkikh.cargologistic.data.PreferencesRequest
import `fun`.gladkikh.cargologistic.preferences.PreferencesImpl
import `fun`.gladkikh.cargologistic.preferences.SharedPrefsManager
import `fun`.gladkikh.cargologistic.data.RemoteRequest
import `fun`.gladkikh.cargologistic.db.AppDatabase
import `fun`.gladkikh.cargologistic.db.DataBaseRequestImpl
import `fun`.gladkikh.cargologistic.db.dao.PrintLabelDao
import `fun`.gladkikh.cargologistic.db.dao.ProductDao
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
        return App.gson
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideProductDao(appDatabase: AppDatabase):ProductDao{
      return appDatabase.productDao()
    }

    @Provides
    @Singleton
    fun providePrintLabelDao(appDatabase: AppDatabase):PrintLabelDao{
        return appDatabase.printLabelDao()
    }

    @Provides
    @Singleton
    fun provideDataBaseRequest(productDao: ProductDao,printLabelDao: PrintLabelDao):DataBaseRequest{
        return DataBaseRequestImpl(productDao,printLabelDao)
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