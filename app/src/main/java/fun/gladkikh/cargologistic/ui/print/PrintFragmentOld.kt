package `fun`.gladkikh.cargologistic.ui.print

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.BaseFragment
import `fun`.gladkikh.cargologistic.common.ui.ext.onEvent
import `fun`.gladkikh.cargologistic.presentation.printOld.PrintFragmentViewModelOld
import `fun`.gladkikh.cargologistic.presentation.printOld.printdialog.StatePrintLabelDialog
import `fun`.gladkikh.cargologistic.presentation.printOld.printerdialog.StatePrinterDialogOld
import `fun`.gladkikh.cargologistic.ui.printlabel.PrintActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.test_print_fragment.*

class PrintFragmentOld : BaseFragment() {
    override val layoutId = R.layout.test_print_fragment

    private lateinit var viewModel: PrintFragmentViewModelOld

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {
            onEvent(getMessageData(), ::handleMessage)
            onEvent(getFailureData(), ::handleFailure)
            onEvent(getProgressData(), ::handleProgress)

            onEvent(getShowChoicePrinterDialog(), ::handleShowChoicePrinterDialog)

            onEvent(getStatePrintLabelDialog(), ::handleStatePrintLabelDialogViewModel)

            //onEvent(getStatePrinterDialog(), ::handleStatePrinterDialogViewModel)
        }

    }

    private fun handleShowChoicePrinterDialog(show: Boolean?) {
        var dialog = activity!!.supportFragmentManager
            .findFragmentByTag(ChoicePrinterDialogFragmentOld.TAG) as? ChoicePrinterDialogFragmentOld

        if (dialog == null) {
            dialog = ChoicePrinterDialogFragmentOld()
        }

        if (show == true) {
            if (!dialog.isVisible) {
                dialog.show(activity!!.supportFragmentManager, ChoicePrinterDialogFragmentOld.TAG)
            }
        } else {
            try {
                dialog.dismiss()
            } catch (e: Exception) {
            }
        }
    }

    fun handleStatePrintLabelDialogViewModel(statePrintLabelDialog: StatePrintLabelDialog?) {
        if (statePrintLabelDialog?.isOpen == true) {
            val dialog =
                activity!!.supportFragmentManager.findFragmentByTag(PrintLabelDialogFragmentOld.TAG)
            if (dialog == null) {
                PrintLabelDialogFragmentOld().show(
                    activity!!.supportFragmentManager,
                    PrintLabelDialogFragmentOld.TAG
                )
            }
        }
    }

    fun handleStatePrinterDialogViewModel(statePrinterDialog: StatePrinterDialogOld?) {
//        if (statePrinterDialog?.isOpen == true) {
//            val dialog =
//                activity!!.supportFragmentManager.findFragmentByTag(PrinterDialogFragment.TAG)
//            if (dialog == null) {
//                PrinterDialogFragment().show(
//                    activity!!.supportFragmentManager,
//                    PrintLabelDialogFragment.TAG
//                )
//            }
//        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as PrintActivity).getBarcodeData().observe(viewLifecycleOwner, Observer {
            viewModel.readBarcode(it)
        })

        btLongOperation.setOnClickListener {
            viewModel.executeLongOperation()
        }

        btOpenPrintLabelDialog.setOnClickListener {
            viewModel.openPintLabelDialog()
        }

        btOpenPrinterDialog.setOnClickListener {
            viewModel.openPinterDialog()
        }


    }
}