package `fun`.gladkikh.cargologistic.domain.usecase

import `fun`.gladkikh.cargologistic.common.interactor.UseCase
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.domain.repository.AccountRepository
import javax.inject.Inject

class TestRemoteRequestUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<String, None>() {
    override suspend fun run(params: None): Either<Failure, String> {
        return accountRepository.testRemoteRequest()
    }
}
