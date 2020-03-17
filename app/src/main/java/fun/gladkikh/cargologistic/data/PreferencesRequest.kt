package `fun`.gladkikh.cargologistic.data


import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity
import `fun`.gladkikh.cargologistic.domain.entity.SettingsEntity

interface PreferencesRequest {
    fun getSettings(): Either<Failure, SettingsEntity>
    fun saveSettings(settingsEntity: SettingsEntity): Either<Failure, None>
    fun getAccountEntity(): Either<Failure, AccountEntity>
    fun saveAccountEntity(account: AccountEntity): Either<Failure, None>
}