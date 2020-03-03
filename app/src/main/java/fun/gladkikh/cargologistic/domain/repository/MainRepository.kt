package `fun`.gladkikh.cargologistic.domain.repository

import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity

interface MainRepository {
    fun getProductByBarcode(barcode: String): Either<Failure, ProductEntity>


}