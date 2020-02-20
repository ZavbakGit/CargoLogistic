package `fun`.gladkikh.cargologistic.domain.usecase
import `fun`.gladkikh.cargologistic.common.interactor.UseCase
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity
import `fun`.gladkikh.cargologistic.domain.repository.AccountRepository
import javax.inject.Inject

class GetProductByBarcodeUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<ProductEntity, String>() {
    override suspend fun run(params: String): Either<Failure, ProductEntity> {
        return accountRepository.getProductByBarcode(params)
    }

}
