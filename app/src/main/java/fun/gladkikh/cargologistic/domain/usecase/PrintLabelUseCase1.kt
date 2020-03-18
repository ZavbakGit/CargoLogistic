package `fun`.gladkikh.cargologistic.domain.usecase
import `fun`.gladkikh.cargologistic.common.interactor.UseCase
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity
import `fun`.gladkikh.cargologistic.domain.entity.PrintLabelEntity
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity
import `fun`.gladkikh.cargologistic.domain.repository.AccountRepository
import java.util.*
import javax.inject.Inject

class PrintLabelUseCase1 @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<PrintLabelEntity, PrintLabelEntity>() {

    override suspend fun run(params: PrintLabelEntity): Either<Failure, PrintLabelEntity> {
        return accountRepository.printLabel1(params)
    }


}
