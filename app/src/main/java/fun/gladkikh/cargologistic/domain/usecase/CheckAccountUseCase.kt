package `fun`.gladkikh.cargologistic.domain.usecase


import `fun`.gladkikh.cargologistic.common.interactor.UseCase
import `fun`.gladkikh.cargologistic.common.type.*
import `fun`.gladkikh.cargologistic.domain.repository.AccountRepository
import javax.inject.Inject

class CheckAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<None, None>() {
    override suspend fun run(params: None): Either<Failure, None> {
        return accountRepository.getAccountEntity().flatMap {
            if (it.password.isNullOrBlank()) {
                return@flatMap Either.Left(AccountFailure())
            } else {
                return@flatMap Either.Right(None())
            }
        }
    }
}
