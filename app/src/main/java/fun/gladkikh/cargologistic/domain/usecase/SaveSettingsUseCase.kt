package `fun`.gladkikh.cargologistic.domain.usecase

import `fun`.gladkikh.cargologistic.common.interactor.UseCase
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.domain.entity.SettingsEntity
import `fun`.gladkikh.cargologistic.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : UseCase<None, SettingsEntity>() {

    override suspend fun run(params: SettingsEntity): Either<Failure, None> {
        return settingsRepository.saveSettings(params)
    }
}
