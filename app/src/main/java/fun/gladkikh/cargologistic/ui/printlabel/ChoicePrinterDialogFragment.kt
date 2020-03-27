package `fun`.gladkikh.cargologistic.ui.printlabel

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.dialog.DialogMVVM
import `fun`.gladkikh.cargologistic.domain.entity.PrinterEntity
import `fun`.gladkikh.cargologistic.presentation.printOld.PrintFragmentViewModelOld
import `fun`.gladkikh.cargologistic.presentation.printlabel.PrintLabelFragmentViewModel
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class ChoicePrinterDialogFragment :
    DialogMVVM<List<PrinterEntity>,PrinterEntity>(){

    companion object{
        const val TAG = "ChoicePrinterDialogFragment"
    }

    private lateinit var viewModel: PrintLabelFragmentViewModel

    private val layoutId = R.layout.test_dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {}
        dialogViewModel = viewModel.choicePrinterDialogViewModel
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val listPrinter = viewModel.listPrinter
        val list = listPrinter.map { it.name }.toTypedArray()
        var currentPrinter = listPrinter.indexOfFirst { it.guid == viewModel.currentPrinter?.guid }

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