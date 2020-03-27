package `fun`.gladkikh.cargologistic.presentation.printOld.printdialog

import `fun`.gladkikh.cargologistic.domain.entity.PrinterEntity
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity
import java.util.*

data class StatePrintLabelDialog(
    val isOpen: Boolean,
    val productEntity: ProductEntity? = null,
    val count: Int? = null,
    val isPositiveEvent:Boolean? = false,
    val currentPrinter:PrinterEntity? = null,
    val dateCreate: Date? = null,
    val barcode:String? = null
)