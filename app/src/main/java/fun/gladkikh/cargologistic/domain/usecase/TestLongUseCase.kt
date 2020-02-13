package `fun`.gladkikh.cargologistic.domain.usecase

import `fun`.gladkikh.cargologistic.common.interactor.UseCase
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import kotlinx.coroutines.delay
import javax.inject.Inject

class TestLongUseCase @Inject constructor() : UseCase<None, Int>() {
    override suspend fun run(params: Int): Either<Failure, None> {
        (0..params).forEach {
            delay(1000)

            if (it == 8) {
                return Either.Left(Failure("Ошибка ${it}"))
            }
        }

        return Either.Right(None())
    }

}