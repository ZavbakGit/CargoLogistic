package `fun`.gladkikh.cargologistic.domain.repository

import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.domain.entity.PrintLabelEntity
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity
import androidx.lifecycle.LiveData
import java.util.*

interface MainRepository {
    fun getProductByBarcode(barcode: String): Either<Failure, ProductEntity>
    fun printLabel(
        guidPrinter: String,
        guidProduct: String,
        barcode: String?,
        dataCreate: Date,
        count: Int
    ): Either<Failure, PrintLabelEntity>

    fun getListPrintEntity():LiveData<PrintLabelEntity>
}