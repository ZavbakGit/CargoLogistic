package `fun`.gladkikh.cargologistic.domain.usecase
import `fun`.gladkikh.cargologistic.common.interactor.UseCase
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.domain.entity.SettingsEntity
import `fun`.gladkikh.cargologistic.domain.repository.SettingsRepository
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : UseCase<SettingsEntity, None>() {

    override suspend fun run(params: None): Either<Failure, SettingsEntity> {
        return settingsRepository.getSettings()
    }
}
