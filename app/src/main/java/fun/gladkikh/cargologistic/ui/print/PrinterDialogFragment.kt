package `fun`.gladkikh.cargologistic.ui.print

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.common.ui.BaseDialog
import `fun`.gladkikh.cargologistic.common.ui.ext.onEvent
import `fun`.gladkikh.cargologistic.presentation.print.PrintFragmentViewModel
import `fun`.gladkikh.cargologistic.presentation.print.printerdialog.StatePrinterDialog
import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog

class PrinterDialogFragment : BaseDialog() {

    companion object {
        const val TAG = "ListPrinterDialog"
    }

    private lateinit var viewModel: PrintFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {
            onEvent(getStatePrinterDialog(), ::handleState)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val listPrinter = viewModel.getListPrinter().value ?: listOf()
        val list = listPrinter.map { it.name }.toTypedArray()
        var currentPrinter = listPrinter.indexOfFirst { it.current == true }

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Выберите принтер")
                .setSingleChoiceItems(
                    list, currentPrinter
                ) { dialog, item ->
                    currentPrinter = item
                }
                .setPositiveButton(
                    "OK"
                ) { dialog, id ->
                    sendResult(currentPrinter)
                }
                .setNegativeButton("Отмена") { dialog, id ->
                    closeDialog()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view?.isFocusableInTouchMode = true
        view?.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                closeDialog()
            }
            return@setOnKeyListener true
        }
    }

    private fun sendResult(id: Int) {
        val list = viewModel.getListPrinter().value ?: listOf()

        viewModel.resultPrinterDialog(
            viewModel.getStatePrinterDialog().value!!.copy(
                isPositiveEvent = true,
                listPrinter = list.mapIndexed { index, printerEntity ->
                    printerEntity.copy(current = index == id)
                }
            )
        )
    }

    private fun closeDialog() {
        viewModel.resultPrinterDialog(
            viewModel.getStatePrinterDialog().value!!
                .copy(isPositiveEvent = false,isOpen = false)
        )
    }

    fun handleState(statePrinterDialog: StatePrinterDialog?) {
        if (statePrinterDialog?.isOpen != true) {
            dismiss()
        }
    }

}