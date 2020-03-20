package `fun`.gladkikh.cargologistic.domain.usecase

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.common.interactor.UseCase
import `fun`.gladkikh.cargologistic.common.type.*
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity
import `fun`.gladkikh.cargologistic.domain.repository.AccountRepository
import javax.inject.Inject

class LoginAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<AccountEntity, String>() {
    override suspend fun run(params: String): Either<Failure, AccountEntity> {

        val account = App.accountEntity ?: return login(params)
        //Если не записан то сразу login
        if (account.guid == null) {
            return login(params)
        }

        //Если пароль равен сохраненному то возвращаем его
        return if (account.password.equals(params)) {
            Either.Right(account)
        } else {
            login(params)
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
            }.map {
                return@map it.copy(password = password.trim())
            }.onNext {
                accountRepository.saveAccountEntity(it)
            }.onNext {
                App.initAccount(it)
            }
    }

}
