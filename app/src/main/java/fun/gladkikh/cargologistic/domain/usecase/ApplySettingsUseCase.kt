package `fun`.gladkikh.cargologistic.domain.usecase
import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.common.interactor.UseCase
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.common.type.flatMap
import `fun`.gladkikh.cargologistic.domain.repository.SettingsRepository
import javax.inject.Inject

class ApplySettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : UseCase<None, None>() {
    override suspend fun run(params: None): Either<Failure, None> {
        return settingsRepository.getSettings().flatMap {
            try {
                if (it.host.isNullOrBlank()){
                    return@flatMap Either.Left(Failure("Не заполнен host"))
                }
                App.initRequestRemote(it.host)
                return@flatMap Either.Right(None())
            } catch (e: Exception) {
                return@flatMap Either.Left(Failure(e.toString()))
            }
        }
    }
}
