package `fun`.gladkikh.cargologistic.ui.printlabel

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.flatMap
import `fun`.gladkikh.cargologistic.common.ui.dialog.DialogMVVM
import `fun`.gladkikh.cargologistic.common.ui.ext.onEvent
import `fun`.gladkikh.cargologistic.common.utils.getDateDMY
import `fun`.gladkikh.cargologistic.common.utils.toStringDMY
import `fun`.gladkikh.cargologistic.domain.entity.PrinterEntity
import `fun`.gladkikh.cargologistic.presentation.printlabel.PrintLabelFragmentViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import kotlinx.android.synthetic.main.print_dialog.*


class PrintLabelDialogFragment :
    DialogMVVM<PrintLabelFragmentViewModel.StatePrintLabelDialog,
            PrintLabelFragmentViewModel.StatePrintLabelDialog>() {
    companion object {
        const val TAG = "PrintLabelDialogFragment"
    }

    private lateinit var viewModel: PrintLabelFragmentViewModel

    private val layoutId = R.layout.print_label_dialog

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
            //onEvent(printLabelDialog.getStateLiveData(),::setStateState)
            onEvent(printLabelDialog.getCurrentPinter(),::handleCurrentPrinter)
        }
        dialogViewModel = viewModel.printLabelDialog

    }

    override fun setStateState(state: PrintLabelFragmentViewModel.StatePrintLabelDialog?) {
        super.setStateState(state)
        tvBarcode.text = state?.barcode?:""
        tvProduct.text = state?.productEntity?.name?:""
        edCount.setText((state?.countLabel ?: 0).toString())
        edPrinter.setText(state?.currentPrinter?.name ?: "Не выбран...")
        edDateCreate.requestFocus()

        if (state?.dateCreate != null) {
            edDateCreate.setText(state.dateCreate.toStringDMY())
            edCount.requestFocus()
        }

    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return super.onCreateDialog(savedInstanceState).apply {
//            requestWindowFeature(Window.FEATURE_NO_TITLE)
//        }
//    }

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
        edDateCreate.setOnFocusChangeListener { view, b ->
            if (b) {
                (view as? EditText)?.selectAll()
            }

        }

        edPrinter.setOnClickListener {
            viewModel.openPrinterDialog()
        }

        btSubmit.setOnClickListener {
            edDateCreate.text.toString().getDateDMY()
                .flatMap {
                    val count = edCount.text.toString().toIntOrNull() ?: 0

                    if (count == 0) {
                        return@flatMap Either.Left(Failure("Не заполнено количество!"))
                    }

                   val state = viewModel.printLabelDialog.getStateLiveData().value!!.copy(
                        countLabel = count,
                        dateCreate = it
                    )


                    return@flatMap Either.Right(state)
                }.either({
                    viewModel.updateFailure(it)
                }, {
                    viewModel.printLabelDialog.onResult(it)
                })
        }
    }

    fun handleCurrentPrinter(printer: PrinterEntity?) {
        edPrinter.setText(printer?.name ?: "Не выбран...")
        if (printer != null){
            dialogViewModel?.setState(viewModel.printLabelDialog.getStateLiveData().value!!.copy(currentPrinter = printer))
        }
    }


}