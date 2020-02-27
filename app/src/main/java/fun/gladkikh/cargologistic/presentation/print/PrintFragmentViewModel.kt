package `fun`.gladkikh.cargologistic.presentation.print

import `fun`.gladkikh.cargologistic.common.presentation.BaseFragmentViewModel
import `fun`.gladkikh.cargologistic.common.type.Message
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.common.type.Progress
import `fun`.gladkikh.cargologistic.common.type.flatMap
import `fun`.gladkikh.cargologistic.common.utils.getDateYMD
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity
import `fun`.gladkikh.cargologistic.domain.entity.PrinterEntity
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity
import `fun`.gladkikh.cargologistic.domain.usecase.GetAccountUseCase
import `fun`.gladkikh.cargologistic.domain.usecase.GetProductByBarcodeUseCase
import `fun`.gladkikh.cargologistic.domain.usecase.PrintLabelUseCase
import `fun`.gladkikh.cargologistic.domain.usecase.TestLongUseCase
import `fun`.gladkikh.cargologistic.presentation.print.printdialog.StatePrintLabelDialog
import `fun`.gladkikh.cargologistic.presentation.print.printerdialog.StatePrinterDialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import java.util.*
import javax.inject.Inject

class PrintFragmentViewModel @Inject constructor(
    private val testLong: TestLongUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val getProductByBarcodeUseCase: GetProductByBarcodeUseCase,
    private val printLabelUseCase: PrintLabelUseCase
) :
    BaseFragmentViewModel() {

    init {
        initAccount()
    }

    //<editor-fold desc="PrintDialog">
    private val statePrintLabelDialog = MutableLiveData<StatePrintLabelDialog>()

    fun getStatePrintLabelDialog(): LiveData<StatePrintLabelDialog> = statePrintLabelDialog
    fun updateStatePrintLabelDialog(state: StatePrintLabelDialog) {
        this.statePrintLabelDialog.postValue(state)
    }

    fun openPintLabelDialog() {
        statePrintLabelDialog.postValue(
            StatePrintLabelDialog(
                isOpen = true,
                count = 1,
                isPositiveEvent = null,
                productEntity = ProductEntity(
                    guid = "111",
                    name = "Помидоры в томатном соусе",
                    barcode = "77785656546",
                    listUnit = listOf()
                )

            )
        )
    }

    fun resultPrintLabelDialog(state: StatePrintLabelDialog?) {
        if (state?.isPositiveEvent != null) {
            if (state.isPositiveEvent == true) {
                printLabel(
                    guidPrinter = listPrinter.value!!.find { it.current == true }!!.guid!!,
                    dateCreate = state.dateCreate!!,
                    barcode = state.barcode ?: "",
                    guidProduct = state.productEntity!!.guid,
                    count = state.count!!
                )
            }
        }

        updateStatePrintLabelDialog(StatePrintLabelDialog(isOpen = false))
    }
    //</editor-fold>

    //<editor-fold desc="PrinterDialog">
    private val statePrinterDialog = MutableLiveData<StatePrinterDialog>()
    private val listPrinter = MutableLiveData<List<PrinterEntity>>()
    fun getListPrinter(): LiveData<List<PrinterEntity>> = listPrinter
    fun updateListPrinter(listPrinter: List<PrinterEntity>?) {
        this.listPrinter.postValue(listPrinter)
    }

    fun getStatePrinterDialog(): LiveData<StatePrinterDialog> = statePrinterDialog
    fun updateStatePrinterDialog(state: StatePrinterDialog) {
        this.statePrinterDialog.postValue(state)
    }

    fun openPinterDialog() {
        statePrinterDialog.postValue(
            StatePrinterDialog(
                isOpen = true,
                isPositiveEvent = null,
                listPrinter = listPrinter.value
            )
        )
    }

    fun resultPrinterDialog(state: StatePrinterDialog?) {
        if (state?.isPositiveEvent != null) {
            if (state.isPositiveEvent == true) {
                updateListPrinter(state.listPrinter)
            }
        }

        updateStatePrinterDialog(StatePrinterDialog(isOpen = false))
    }
    //</editor-fold>

    private fun printLabel(
        guidProduct: String,
        dateCreate: Date,
        barcode: String,
        count: Int,
        guidPrinter: String
    ) {
        updateProgress(Progress(true, "Печатаем этикетку!"))

        val param = PrintLabelUseCase.Params(
            guidProduct = guidProduct,
            dataCreate = dateCreate,
            barcode = barcode,
            count = count,
            guidPrinter = guidPrinter
        )

        printLabelUseCase(param, viewModelScope) {
            updateProgress(Progress(false))
            it.either(::updateFailure, ::handlePrintLabel)
        }
    }

    private fun handlePrintLabel(date: Date) {
        updateMessage(Message(date.toString()))
    }

    private fun handleLongOperation(none: None) {
        openPintLabelDialog()
    }

    private fun initAccount() {
        updateProgress(Progress(true, "Получаем данные Акаунта!"))
        getAccountUseCase(None(), viewModelScope) {
            updateProgress(Progress(false))
            it.either(::updateFailure, ::handleInitAccount)
        }
    }

    private fun handleInitAccount(accountEntity: AccountEntity) {
        updateListPrinter(accountEntity.settings?.listPrinter)
    }

    private fun handleGetProductByBarcode(productEntity: ProductEntity, barcode: String,dateCreate:Date? = null) {
        statePrintLabelDialog.postValue(
            StatePrintLabelDialog(
                isOpen = true,
                count = 1,
                isPositiveEvent = null,
                productEntity = productEntity,
                barcode = barcode,
                dateCreate = dateCreate
            )
        )
    }

    fun executeLongOperation() {
        updateProgress(Progress(true, "Долгая операция!"))
        testLong(5, viewModelScope) {
            updateProgress(Progress(false))
            it.either(::updateFailure, ::handleLongOperation)
        }
    }

    fun readBarcode(barcode: String?) {
        if (statePrintLabelDialog.value?.isOpen != true){
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
                handleGetProductByBarcode(product, barcode,dateCreate)
            }
        }
    }

}