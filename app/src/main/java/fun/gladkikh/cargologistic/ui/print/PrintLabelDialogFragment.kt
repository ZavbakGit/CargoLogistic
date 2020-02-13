package `fun`.gladkikh.cargologistic.ui.print

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.BaseDialog
import `fun`.gladkikh.cargologistic.common.ui.ext.onEvent
import `fun`.gladkikh.cargologistic.domain.entity.PrinterEntity
import `fun`.gladkikh.cargologistic.presentation.print.PrintFragmentViewModel
import `fun`.gladkikh.cargologistic.presentation.print.printdialog.StatePrintLabelDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Printer
import android.view.*
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import kotlinx.android.synthetic.main.print_dialog.*


class PrintLabelDialogFragment : BaseDialog() {
    companion object {
        const val TAG = "PrintLabelDialogFragment"
    }

    private lateinit var viewModel: PrintFragmentViewModel
    private val layoutId = R.layout.print_dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {
            onEvent(getListPrinter(), ::handleListPrinter)
            onEvent(getStatePrintLabelDialog(), ::handleState)
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val affineFormats = listOf("[00].[00].[00]")

        val listener =
            MaskedTextChangedListener.installOn(
                edDateCreate,
                "[00].[00].[00]",
                affineFormats,
                AffinityCalculationStrategy.WHOLE_STRING,
                object : MaskedTextChangedListener.ValueListener {
                    override fun onTextChanged(
                        maskFilled: Boolean,
                        extractedValue: String,
                        formattedText: String
                    ) {
                        //logValueListener(maskFilled, extractedValue, formattedText)
                        //checkBox.setChecked(maskFilled)
                    }
                }
            )

        edDateCreate.hint = listener.placeholder()



        view?.isFocusableInTouchMode = true
        view?.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                closeDialog()
            }
            return@setOnKeyListener true
        }

        edPrinter.setOnClickListener {
            viewModel.openPinterDialog()
        }

        btSubmit.setOnClickListener {
            viewModel.resultPrintLabelDialog(
                viewModel.getStatePrintLabelDialog().value!!.copy(
                    isPositiveEvent = true,
                    count = edCount.text.toString().toIntOrNull() ?: 0
                )
            )
        }

    }


    private fun closeDialog() {
        viewModel.resultPrintLabelDialog(
            viewModel.getStatePrintLabelDialog().value!!.copy(isPositiveEvent = false)
        )
    }

    fun handleListPrinter(listPrinter: List<PrinterEntity>?) {
        val currentPrinter = listPrinter?.find {
            it.current == true
        }

        edPrinter.setText(currentPrinter?.name ?: "Не выбран...")
    }

    fun handleState(statePrintDialog: StatePrintLabelDialog?) {
        if (statePrintDialog?.isOpen != true) {
            dismiss()
        } else {
            tvBarcode.text = statePrintDialog.productEntity?.barcode ?: ""
            tvProduct.text = statePrintDialog.productEntity?.name ?: ""
            edCount.setText((statePrintDialog.count ?: 0).toString())
        }
    }
}