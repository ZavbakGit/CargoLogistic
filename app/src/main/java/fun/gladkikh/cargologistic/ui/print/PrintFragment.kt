package `fun`.gladkikh.cargologistic.ui.print

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.BaseFragment
import `fun`.gladkikh.cargologistic.common.ui.ext.onEvent
import `fun`.gladkikh.cargologistic.presentation.print.PrintFragmentViewModel
import `fun`.gladkikh.cargologistic.presentation.print.printdialog.StatePrintLabelDialog
import `fun`.gladkikh.cargologistic.presentation.print.printerdialog.StatePrinterDialog
import android.os.Bundle
import kotlinx.android.synthetic.main.test_print_fragment.*

class PrintFragment : BaseFragment() {
    override val layoutId = R.layout.test_print_fragment

    private lateinit var viewModel: PrintFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {
            onEvent(getMessageData(), ::handleMessage)
            onEvent(getFailureData(), ::handleFailure)
            onEvent(getProgressData(), ::handleProgress)

            onEvent(getStatePrintLabelDialog(), ::handleStatePrintLabelDialogViewModel)
            onEvent(getStatePrinterDialog(),::handleStatePrinterDialogViewModel)
        }

    }

    fun handleStatePrintLabelDialogViewModel(statePrintLabelDialog: StatePrintLabelDialog?) {
        if (statePrintLabelDialog?.isOpen == true) {
            val dialog =
                activity!!.supportFragmentManager.findFragmentByTag(PrintLabelDialogFragment.TAG)
            if (dialog == null) {
                PrintLabelDialogFragment().show(
                    activity!!.supportFragmentManager,
                    PrintLabelDialogFragment.TAG
                )
            }
        }
    }

    fun handleStatePrinterDialogViewModel(statePrinterDialog: StatePrinterDialog?) {
        if (statePrinterDialog?.isOpen == true) {
            val dialog =
                activity!!.supportFragmentManager.findFragmentByTag(PrinterDialogFragment.TAG)
            if (dialog == null) {
                PrinterDialogFragment().show(
                    activity!!.supportFragmentManager,
                    PrintLabelDialogFragment.TAG
                )
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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