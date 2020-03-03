package `fun`.gladkikh.cargologistic.domain.repository

import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.domain.entity.OrderEntity
import `fun`.gladkikh.cargologistic.domain.entity.PalletEntity

interface PalletPackingRepository {
    fun getOrderByBarcode(barcode: String): Either<Failure, OrderEntity>
    fun getPalletByBarcode(barcode: String): Either<Failure, PalletEntity>
}