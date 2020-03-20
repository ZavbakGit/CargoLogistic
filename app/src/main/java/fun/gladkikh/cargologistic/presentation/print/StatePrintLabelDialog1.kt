package `fun`.gladkikh.cargologistic.presentation.print

import `fun`.gladkikh.cargologistic.domain.entity.PrinterEntity
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity
import java.util.*

data class StatePrintLabelDialog1(
    val productEntity: ProductEntity,
    val count: Int = 1,
    val currentPrinter:PrinterEntity,
    val dateCreate: Date? = null,
    val barcode:String? = null
)