package `fun`.gladkikh.cargologistic.presentation.setting

import `fun`.gladkikh.cargologistic.common.presentation.BaseFragmentViewModel
import `fun`.gladkikh.cargologistic.common.type.Message
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.common.type.Progress
import `fun`.gladkikh.cargologistic.domain.usecase.ApplySettingsUseCase
import androidx.lifecycle.viewModelScope
import javax.inject.Inject

class SettingFragmentViewModel @Inject constructor(
    private val applySettingsUseCase: ApplySettingsUseCase
) : BaseFragmentViewModel() {

    fun applySetting() {
        updateProgress(Progress(true, "Обновляем!"))
        applySettingsUseCase(None(), viewModelScope) { either ->
            either.either(::handleErrorViewModel) {
                handleMessageViewModel(Message("Обновили!"))
            }
            updateProgress(Progress(false))
        }
    }

}