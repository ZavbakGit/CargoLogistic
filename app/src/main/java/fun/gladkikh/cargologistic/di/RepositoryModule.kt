package `fun`.gladkikh.cargologistic.di

import `fun`.gladkikh.cargologistic.data.AccountRepositoryImpl
import `fun`.gladkikh.cargologistic.data.Preferences
import `fun`.gladkikh.cargologistic.data.SettingsRepositoryImpl
import `fun`.gladkikh.cargologistic.domain.repository.AccountRepository
import `fun`.gladkikh.cargologistic.domain.repository.SettingsRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(preferences: Preferences): SettingsRepository {
        return SettingsRepositoryImpl(preferences)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(preferences: Preferences,
                                 gson: Gson
    ): AccountRepository {
        return AccountRepositoryImpl(preferences,gson)
    }

}