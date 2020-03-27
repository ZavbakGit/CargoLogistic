package `fun`.gladkikh.cargologistic.presentation.printOld

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.common.presentation.BaseFragmentViewModel
import `fun`.gladkikh.cargologistic.common.type.Message
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.common.type.Progress
import `fun`.gladkikh.cargologistic.common.type.SingleLiveEvent
import `fun`.gladkikh.cargologistic.common.ui.dialog.DialogMVVM
import `fun`.gladkikh.cargologistic.common.utils.getDateYMD
import `fun`.gladkikh.cargologistic.db.createGuid
import `fun`.gladkikh.cargologistic.domain.entity.*
import `fun`.gladkikh.cargologistic.domain.usecase.*
import `fun`.gladkikh.cargologistic.presentation.printOld.printdialog.StatePrintLabelDialog
import `fun`.gladkikh.cargologistic.presentation.printOld.printerdialog.StatePrinterDialogOld
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import java.util.*
import javax.inject.Inject

class PrintFragmentViewModelOld @Inject constructor(
    private val testLong: TestLongUseCase,
    private val getProductByBarcodeUseCase: GetProductByBarcodeUseCase,
    private val printLabelUseCase: PrintLabelUseCase

    ) : BaseFragmentViewModel() {

    private val listPrinter = MutableLiveData<List<PrinterEntity>>()
    private val showChoicePrinterDialog = SingleLiveEvent<Boolean>()
    private val showPrintLabelDialog1 = SingleLiveEvent<Boolean>()

    fun getShowChoicePrinterDialog():LiveData<Boolean> = showChoicePrinterDialog
    fun getShowPrintLabelDialog1():LiveData<Boolean> = showPrintLabelDialog1

    val choicePrinterDialogViewModel
            = object : DialogMVVM.DialogViewModel<List<PrinterEntity>, PrinterEntity>() {

        override fun onResult(result: PrinterEntity) {
            super.onResult(result)
            printLabelDialogViewModel.setState(printLabelDialogViewModel.getStateLiveData().value!!.copy(currentPrinter = result))
            showChoicePrinterDialog.postValue(false)
        }
    }

    val printLabelDialogViewModel
            = object : DialogMVVM.DialogViewModel<StatePrintLabelDialog1, StatePrintLabelDialog1>() {
    }




    private val statePrintLabelDialog = MutableLiveData<StatePrintLabelDialog>()
    private val statePrinterDialog = MutableLiveData<StatePrinterDialogOld>()

    init {
        initAccount()
    }

    private fun initAccount() {
        updateListPrinter(App.accountEntity!!.settings!!.listPrinter)
    }






    //<editor-fold desc="PrintDialog">

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
                    listBarcode = listOf(BarcodeEntity("77785656546")),
                    listUnit = listOf()
                )

            )
        )
    }
    fun resultPrintLabelDialog(state: StatePrintLabelDialog?) {
        if (state?.isPositiveEvent != null) {
            if (state.isPositiveEvent == true) {
                printLabel(
                    PrintLabelEntity(
                        guid = createGuid(),
                        datePrint = null,
                        printer = listPrinter.value!!.find { it.current == true }!!,
                        barcodeRead = state.barcode ?: "",
                        countLabel = state.count!!,
                        product = state.productEntity!!,
                        dateCreate = state.dateCreate!!
                    )
                )
            }
        }

        updateStatePrintLabelDialog(StatePrintLabelDialog(isOpen = false))
    }
    //</editor-fold>

    //<editor-fold desc="PrinterDialog">


    fun getListPrinter(): LiveData<List<PrinterEntity>> = listPrinter
    fun updateListPrinter(listPrinter: List<PrinterEntity>?) {
        this.listPrinter.postValue(listPrinter)
    }

    fun getStatePrinterDialog(): LiveData<StatePrinterDialogOld> = statePrinterDialog
    fun updateStatePrinterDialog(state: StatePrinterDialogOld) {
        this.statePrinterDialog.postValue(state)
    }

    fun openPinterDialog() {
        showChoicePrinterDialog.postValue(true)

//        statePrinterDialog.postValue(
//            StatePrinterDialog(
//                isOpen = true,
//                isPositiveEvent = null,
//                listPrinter = listPrinter.value
//            )
//        )
    }

    fun resultPrinterDialog(state: StatePrinterDialogOld?) {
        if (state?.isPositiveEvent != null) {
            if (state.isPositiveEvent == true) {
                updateListPrinter(state.listPrinter)
            }
        }

        updateStatePrinterDialog(StatePrinterDialogOld(isOpen = false))
    }
    //</editor-fold>

    private fun printLabel(
        printLabelEntity: PrintLabelEntity
    ) {
        printLabelUseCase(printLabelEntity, viewModelScope) {
            updateProgress(Progress(false))
            it.either(::updateFailure, ::handlePrintLabel1)
        }
    }





    private fun handlePrintLabel1(printLabelEntity: PrintLabelEntity) {
        updateMessage(Message(printLabelEntity.toString()))
    }

    private fun handleLongOperation(none: None) {
        openPintLabelDialog()
    }



//    private fun handleInitAccount(accountEntity: AccountEntity) {
//        updateListPrinter(accountEntity.settings?.listPrinter)
//    }

    private fun handleGetProductByBarcode(
        productEntity: ProductEntity,
        barcode: String,
        dateCreate: Date? = null
    ) {
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
        if (statePrintLabelDialog.value?.isOpen != true) {
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

}