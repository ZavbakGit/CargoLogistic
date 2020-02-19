package `fun`.gladkikh.cargologistic.domain.usecase
import `fun`.gladkikh.cargologistic.common.interactor.UseCase
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity
import `fun`.gladkikh.cargologistic.domain.repository.AccountRepository
import java.util.*
import javax.inject.Inject

class PrintLabelUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<Date, PrintLabelUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, Date> {
        return accountRepository.printLabel(
            guidPrinter = params.guidPrinter,
            guidProduct = params.guidProduct,
            count = params.count,
            barcode = params.barcode,
            dataCreate = params.dataCreate
        )
    }

    data class Params(val guidPrinter: String,
                      val guidProduct: String,
                      val barcode: String?,
                      val dataCreate: Date,
                      val count: Int)
}
