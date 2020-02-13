package `fun`.gladkikh.cargologistic.ui.print.old

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.BaseDialogOld

import `fun`.gladkikh.cargologistic.presentation.print.old.TestPrintFragmentViewModel
import `fun`.gladkikh.cargologistic.ui.ListPrinterDialog
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn
import com.redmadrobot.inputmask.MaskedTextChangedListener.ValueListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import kotlinx.android.synthetic.main.print_dialog.*


class PrintDialogOld : BaseDialogOld() {
    companion object {
        const val TAG = "PrintDialog"
        fun newInstance(): PrintDialogOld {
            val dialog =
                PrintDialogOld()
            val args = Bundle().apply {
                //putString(EXTRA_TITLE, title)

            }
            dialog.arguments = args
            return dialog
        }
    }

    private lateinit var viewModel: TestPrintFragmentViewModel
    override val layoutId = R.layout.print_dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {
            //onEvent(getStateDialog(), ::handleStateDialog)
        }

    }

//    fun handleStateDialog(statePrintDialog: StatePrintDialog?) {
//        if (statePrintDialog?.isOpen != true) {
//            dismiss()
//        } else {
//            tvBarcode.text = statePrintDialog.productEntity?.barcode ?: ""
//            tvProduct.text = statePrintDialog.productEntity?.name ?: ""
//            edCount.setText((statePrintDialog.count ?: 0).toString())
//        }
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view?.isFocusableInTouchMode = true
        view?.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                closeDialog(false)
            }
            return@setOnKeyListener true
        }

        btSubmit.setOnClickListener {
            closeDialog(true)
        }

        edPrinter.setOnClickListener {
            val dialog = activity!!.supportFragmentManager.findFragmentByTag(ListPrinterDialog.TAG)
            if (dialog == null) {
                ListPrinterDialog().show(
                    activity!!.supportFragmentManager,
                    ListPrinterDialog.TAG
                )
            }
        }

        val affineFormats = listOf("[00].[00].[00]")
        //val affineFormats = listOf<String>()

        val listener =
            installOn(
                edDateCreate,
                "[00].[00].[00]",
                affineFormats,
                AffinityCalculationStrategy.WHOLE_STRING,
                object : ValueListener {
                    override fun onTextChanged(
                        maskFilled: Boolean,
                        extractedValue: String,
                        formattedText: String
                    ) {
                        logValueListener(maskFilled, extractedValue, formattedText)
                        //checkBox.setChecked(maskFilled)
                    }
                }
            )

        edDateCreate.hint = listener.placeholder()
    }

    private fun logValueListener(
        maskFilled: Boolean,
        extractedValue: String,
        formattedText: String
    ) {

        Log.d("anit", extractedValue)
        Log.d("anit", maskFilled.toString())
        Log.d("anit", formattedText)
    }

    private fun closeDialog(isPositiveEvent: Boolean) {
//        viewModel.updatePrintStateDialog(
//            viewModel.getStateDialog().value!!
//                .copy(
//                    isOpen = false,
//                    count = edCount.text.toString().toIntOrNull() ?: 0,
//                    isPositiveEvent = isPositiveEvent
//                )
//        )
    }
}