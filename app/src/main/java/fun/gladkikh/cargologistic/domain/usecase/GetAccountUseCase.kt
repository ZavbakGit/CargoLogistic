package `fun`.gladkikh.cargologistic.domain.usecase
import `fun`.gladkikh.cargologistic.common.interactor.UseCase
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity
import `fun`.gladkikh.cargologistic.domain.repository.AccountRepository
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<AccountEntity, None>() {
    override suspend fun run(params: None): Either<Failure, AccountEntity> {
        return accountRepository.getAccountEntity()
    }
}
