package `fun`.gladkikh.cargologistic.domain.repository


import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity

interface AccountRepository {
    fun getAccountEntity(): Either<Failure, AccountEntity>
    fun saveAccountEntity(account: AccountEntity): Either<Failure, None>
    fun login(password: String): Either<Failure, AccountEntity>
    fun testRemoteRequest(): Either<Failure, String>
}