package `fun`.gladkikh.cargologistic.presentation.printOld.printerdialog

import `fun`.gladkikh.cargologistic.domain.entity.PrinterEntity

data class StatePrinterDialogOld(
    val isOpen: Boolean,
    val listPrinter: List<PrinterEntity>? = null,
    val isPositiveEvent:Boolean? = false
)