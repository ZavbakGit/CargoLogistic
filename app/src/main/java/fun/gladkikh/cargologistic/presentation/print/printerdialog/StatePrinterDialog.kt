package `fun`.gladkikh.cargologistic.presentation.print.printerdialog

import `fun`.gladkikh.cargologistic.domain.entity.PrinterEntity

data class StatePrinterDialog(
    val isOpen: Boolean,
    val listPrinter: List<PrinterEntity>? = null,
    val isPositiveEvent:Boolean? = false
)