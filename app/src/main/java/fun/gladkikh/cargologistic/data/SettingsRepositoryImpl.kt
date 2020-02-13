package `fun`.gladkikh.cargologistic.data


import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.domain.entity.SettingsEntity
import `fun`.gladkikh.cargologistic.domain.repository.SettingsRepository

class SettingsRepositoryImpl(private val preferences: Preferences) : SettingsRepository {
    override fun getSettings(): Either<Failure, SettingsEntity> {
       return preferences.getSettings()
    }

    override fun saveSettings(settingsEntity: SettingsEntity): Either<Failure, None> {
        return preferences.saveSettings(settingsEntity)
    }
}