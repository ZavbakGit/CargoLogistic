package `fun`.gladkikh.cargologistic.presentation.print

import `fun`.gladkikh.cargologistic.common.presentation.BaseFragmentViewModel
import `fun`.gladkikh.cargologistic.common.type.Message
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.common.type.Progress
import `fun`.gladkikh.cargologistic.domain.entity.PrinterEntity
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity
import `fun`.gladkikh.cargologistic.domain.usecase.TestLongUseCase
import `fun`.gladkikh.cargologistic.presentation.print.printdialog.StatePrintLabelDialog
import `fun`.gladkikh.cargologistic.presentation.print.printerdialog.StatePrinterDialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import javax.inject.Inject

class PrintFragmentViewModel @Inject constructor(private val testLong: TestLongUseCase) :
    BaseFragmentViewModel() {

    private val listPrinter = MutableLiveData<List<PrinterEntity>>()
    fun getListPrinter(): LiveData<List<PrinterEntity>> = listPrinter

    fun updateListPrinter(listPrinter: List<PrinterEntity>?) {
        this.listPrinter.postValue(listPrinter)
    }


    init {
        val list = listOf(
            PrinterEntity(
                guid = "111",
                name = "Принтер №1 Маленикая этикетка",
                current = false
            ),
            PrinterEntity(
                guid = "222",
                name = "Принтер №2 Маленикая этикетка",
                current = true
            )
        )

        listPrinter.postValue(list)
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
                    barcode = "77785656546"
                )

            )
        )
    }

    fun resultPrintLabelDialog(state: StatePrintLabelDialog?) {
        if (state?.isPositiveEvent != null) {
            if (state.isPositiveEvent == true) {
                try {
                    updateMessage(Message(state.toString()))
                } catch (e: Exception) {
                }
            }

            this.statePrintLabelDialog.postValue(StatePrintLabelDialog(isOpen = false))
        }
    }
    //</editor-fold>

    //<editor-fold desc="PrinterDialog">
    private val statePrinterDialog = MutableLiveData<StatePrinterDialog>()

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
                try {
                    updateListPrinter(state.listPrinter)
                } catch (e: Exception) {
                }
            }

            this.statePrinterDialog.postValue(StatePrinterDialog(isOpen = false))
        }
    }
    //</editor-fold>

    fun executeLongOperation() {
        updateProgress(Progress(true, "Получаем данные!"))
        testLong(5, viewModelScope) {
            updateProgress(Progress(false))
            it.either(::updateFailure, ::handleLongOperation)
        }
    }

    private fun handleLongOperation(none: None) {
        //updateMessage(Message("Выполнили долгую операцию!"))
        openPintLabelDialog()
    }

}