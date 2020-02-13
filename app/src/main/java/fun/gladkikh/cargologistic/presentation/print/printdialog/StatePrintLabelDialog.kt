package `fun`.gladkikh.cargologistic.presentation.print.printdialog

import `fun`.gladkikh.cargologistic.domain.entity.PrinterEntity
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity

data class StatePrintLabelDialog(
    val isOpen: Boolean,
    val productEntity: ProductEntity? = null,
    val count: Int? = null,
    val isPositiveEvent:Boolean? = false,
    val currentPrinter:PrinterEntity? = null
)