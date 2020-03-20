package `fun`.gladkikh.cargologistic.ui.print

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.dialog.DialogMVVM
import `fun`.gladkikh.cargologistic.domain.entity.PrinterEntity
import `fun`.gladkikh.cargologistic.presentation.print.PrintFragmentViewModel
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class ChoicePrinterDialogFragment :DialogMVVM<List<PrinterEntity>,PrinterEntity>(){

    companion object{
        const val TAG = "ChoicePrinterDialogFragment"
    }

    private lateinit var viewModel: PrintFragmentViewModel

    private val layoutId = R.layout.test_dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {}
        dialogViewModel = viewModel.choicePrinterDialogViewModel
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
                    sendResult(listPrinter[item])
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}