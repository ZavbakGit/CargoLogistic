package `fun`.gladkikh.cargologistic.domain.entity

import java.util.*

data class PrintLabelEntity(
    val guid: String,
    val product: ProductEntity,
    val barcodeRead: String,
    val printer: PrinterEntity,
    val countLabel: Int,
    val dateCreate: Date,
    val datePrint: Date? = null
)