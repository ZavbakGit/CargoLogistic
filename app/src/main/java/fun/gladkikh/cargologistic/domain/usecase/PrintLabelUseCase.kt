package `fun`.gladkikh.cargologistic.domain.usecase
import `fun`.gladkikh.cargologistic.common.interactor.UseCase
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.domain.entity.PrintLabelEntity
import `fun`.gladkikh.cargologistic.domain.repository.AccountRepository
import javax.inject.Inject

class PrintLabelUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<PrintLabelEntity, PrintLabelEntity>() {

    override suspend fun run(params: PrintLabelEntity): Either<Failure, PrintLabelEntity> {
        return accountRepository.printLabel(params)
    }
}
