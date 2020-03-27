package `fun`.gladkikh.cargologistic.presentation.printlabel

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.common.presentation.BaseFragmentViewModel
import `fun`.gladkikh.cargologistic.common.type.Message
import `fun`.gladkikh.cargologistic.common.type.Progress
import `fun`.gladkikh.cargologistic.common.type.SingleLiveEvent
import `fun`.gladkikh.cargologistic.common.ui.dialog.DialogMVVM
import `fun`.gladkikh.cargologistic.common.utils.getDateYMD
import `fun`.gladkikh.cargologistic.db.createGuid
import `fun`.gladkikh.cargologistic.domain.entity.*
import `fun`.gladkikh.cargologistic.domain.usecase.GetListPrintLabelLiveData
import `fun`.gladkikh.cargologistic.domain.usecase.GetProductByBarcodeUseCase
import `fun`.gladkikh.cargologistic.domain.usecase.PrintLabelUseCase
import `fun`.gladkikh.cargologistic.presentation.printOld.printdialog.StatePrintLabelDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import java.util.*
import javax.inject.Inject

class PrintLabelFragmentViewModel @Inject constructor(
    private val getProductByBarcodeUseCase: GetProductByBarcodeUseCase,
    private val printLabelUseCase: PrintLabelUseCase,
    private val getListPrintLabelLiveData: GetListPrintLabelLiveData


) : BaseFragmentViewModel() {


    fun getListPrintLabelLiveData() = getListPrintLabelLiveData.getListPrintLabel()

    var listPrinter = App.accountEntity?.settings?.listPrinter ?: listOf()
    var currentPrinter = listPrinter.find { it.current == true }

    //<editor-fold desc="PrinterDialog">
    private val showChoicePrinterDialog = SingleLiveEvent<Boolean>()
    fun getShowChoicePrinterDialog(): LiveData<Boolean> = showChoicePrinterDialog
    val choicePrinterDialogViewModel =
        object : DialogMVVM.DialogViewModel<List<PrinterEntity>, PrinterEntity>() {

            override fun onResult(result: PrinterEntity) {
                super.onResult(result)
                currentPrinter = result
                printLabelDialog.updateCurrentPinter(result)
                //updateMessage(Message(result.toString()))
                showChoicePrinterDialog.postValue(false)
            }
        }

    fun openPrinterDialog() {
        showChoicePrinterDialog.postValue(true)
    }
    //</editor-fold>

    //<editor-fold desc="PrintLabel">
    private val showPrintLabelDialog = SingleLiveEvent<Boolean>()
    fun getShowPrintLabelDialog(): LiveData<Boolean> = showPrintLabelDialog

    val printLabelDialog = PrintLabelDialog()

    inner class PrintLabelDialog :
        DialogMVVM.DialogViewModel<StatePrintLabelDialog, StatePrintLabelDialog>() {
        private val currentPinter = MutableLiveData<PrinterEntity>()
        fun getCurrentPinter(): LiveData<PrinterEntity> = currentPinter
        fun updateCurrentPinter(printer: PrinterEntity) {
            currentPinter.postValue(printer)
        }

        override fun onResult(result: StatePrintLabelDialog) {
            super.onResult(result)
            printLabel(
                PrintLabelEntity(
                    guid = createGuid(),
                    dateCreate = result.dateCreate!!,
                    countLabel = result.countLabel,
                    product = result.productEntity,
                    barcodeRead = result.barcode ?: "",
                    printer = result.currentPrinter
                )
            )
            showPrintLabelDialog.postValue(false)
        }
    }

    data class StatePrintLabelDialog(
        val barcode: String?,
        val currentPrinter: PrinterEntity,
        val productEntity: ProductEntity,
        val dateCreate: Date? = null,
        val countLabel: Int
    )

    fun openPrintLabelDialog() {
        printLabelDialog.setState(
            StatePrintLabelDialog(
                barcode = "54165465465",
                countLabel = 1,
                currentPrinter = currentPrinter ?: listPrinter.first(),
                productEntity = ProductEntity(
                    guid = "5456465",
                    name = "Тестовый товар",
                    listBarcode = (0..3).map { BarcodeEntity("$it" + 55555) },
                    listUnit = (0..2).map {
                        UnitEntity(
                            code = it.toString(),
                            name = "Шт $it",
                            current = it == 2,
                            coeff = it.toFloat()
                        )
                    }
                )
            )
        )
        showPrintLabelDialog.postValue(true)
    }
    //</editor-fold>


    fun readBarcode(barcode: String) {
        updateMessage(Message(barcode))

        if (printLabelDialog.isOpen != true) {
            if (!barcode.isNullOrBlank()) {
                if (barcode.take(2).toUpperCase() != "TA") {
                    getProductByBarcode(barcode, null)

                } else {
                    barcode.substring((2..7)).getDateYMD()
                        .either(::updateFailure) {
                            getProductByBarcode(barcode.substring(8 until barcode.length), it)
                        }
                }
            }
        }
    }

    private fun getProductByBarcode(barcode: String, dateCreate: Date?) {
        updateProgress(Progress(true, "Запрос товара!"))
        getProductByBarcodeUseCase(barcode, viewModelScope) {
            updateProgress(Progress(false))
            it.either(::updateFailure) { product ->
                handleGetProductByBarcode(product, barcode, dateCreate)
            }
        }
    }

    private fun printLabel(
        printLabelEntity: PrintLabelEntity
    ) {
        printLabelUseCase(printLabelEntity, viewModelScope) {
            updateProgress(Progress(false))
            it.either(::updateFailure, ::handlePrintLabel)
        }
    }

    private fun handlePrintLabel(printLabelEntity: PrintLabelEntity) {
        //updateMessage(Message(printLabelEntity.toString()))
    }

    private fun handleGetProductByBarcode(
        productEntity: ProductEntity,
        barcode: String,
        dateCreate: Date? = null
    ) {

        printLabelDialog.setState(
            StatePrintLabelDialog(
                barcode = barcode,
                countLabel = 1,
                currentPrinter = currentPrinter ?: listPrinter.first(),
                productEntity = productEntity,
                dateCreate = dateCreate
            )
        )
        showPrintLabelDialog.postValue(true)
    }


}