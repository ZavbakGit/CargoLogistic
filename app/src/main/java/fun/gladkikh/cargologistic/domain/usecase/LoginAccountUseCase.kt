package `fun`.gladkikh.cargologistic.domain.usecase
import `fun`.gladkikh.cargologistic.common.interactor.UseCase
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.common.type.flatMap
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity
import `fun`.gladkikh.cargologistic.domain.repository.AccountRepository
import javax.inject.Inject

class LoginAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<AccountEntity, String>() {
    override suspend fun run(params: String): Either<Failure, AccountEntity> {
        val account = accountRepository.getAccountEntity()

        if (account.isLeft) {
            return login(params)
        } else {

            return account.flatMap { accountEntity ->
                if (accountEntity.password == params) {
                    return@flatMap Either.Right(accountEntity)
                } else {
                    return@flatMap login(params)
                }
            }
        }
    }


    private fun login(password: String): Either<Failure, AccountEntity> {
        val accountEntity = AccountEntity(
            user = null,
            guid = null,
            settings = null,
            password = null
        )

        return accountRepository.saveAccountEntity(accountEntity)
            .flatMap {
                accountRepository.login(password)
            }.flatMap {
                accountRepository.saveAccountEntity(it)
                return@flatMap Either.Right(it)
            }
    }


}
