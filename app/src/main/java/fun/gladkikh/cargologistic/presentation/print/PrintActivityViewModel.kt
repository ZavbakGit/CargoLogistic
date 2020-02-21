package `fun`.gladkikh.cargologistic.presentation.print

import `fun`.gladkikh.cargologistic.common.presentation.BaseActivityViewModel
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.domain.entity.SettingsEntity
import `fun`.gladkikh.cargologistic.domain.usecase.GetSettingsUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import javax.inject.Inject

class PrintActivityViewModel @Inject constructor(private val getSettingsUseCase: GetSettingsUseCase) :
    BaseActivityViewModel() {

    private val settingsData = MutableLiveData<SettingsEntity>()
    private fun updateSettings(settings: SettingsEntity) {
        settingsData.postValue(settings)
    }

    fun getSettingsData(): LiveData<SettingsEntity> = settingsData

    init {
        readSettings()
    }

    fun readSettings() {
        getSettingsUseCase(None(), viewModelScope) {
            it.either(::handleErrorViewModel, ::handleReadSettings)
        }
    }


    private fun handleReadSettings(settingsEntity: SettingsEntity) {
        updateSettings(settingsEntity)
    }

}