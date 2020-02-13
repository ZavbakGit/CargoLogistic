package `fun`.gladkikh.cargologistic.domain.repository


import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.domain.entity.SettingsEntity

interface SettingsRepository {
    fun getSettings(): Either<Failure, SettingsEntity>
    fun saveSettings(settingsEntity: SettingsEntity):Either<Failure, None>
}