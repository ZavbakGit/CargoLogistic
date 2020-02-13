package `fun`.gladkikh.cargologistic.preferences


import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.data.Preferences
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity
import `fun`.gladkikh.cargologistic.domain.entity.SettingsEntity

import javax.inject.Inject

class PreferencesImpl @Inject constructor (private val prefsManager: SharedPrefsManager):
    Preferences {
    override fun getSettings(): Either<Failure, SettingsEntity> {
        return prefsManager.getSettings()
    }

    override fun saveSettings(settingsEntity: SettingsEntity): Either<Failure, None> {
        return prefsManager.saveSettings(settingsEntity)
    }

    override fun getAccountEntity(): Either<Failure, AccountEntity> {
        return prefsManager.getAccountEntity()
    }

    override fun saveAccountEntity(account: AccountEntity): Either<Failure, None> {
        return prefsManager.saveAccountEntity(account)
    }
}