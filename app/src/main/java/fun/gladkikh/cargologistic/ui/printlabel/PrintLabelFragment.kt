package `fun`.gladkikh.cargologistic.ui.printlabel

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.BaseFragment
import `fun`.gladkikh.cargologistic.common.ui.ext.onEvent
import `fun`.gladkikh.cargologistic.common.ui.ext.showDialog
import `fun`.gladkikh.cargologistic.common.utils.toSimpleDateTime
import `fun`.gladkikh.cargologistic.domain.entity.PrintLabelEntity
import `fun`.gladkikh.cargologistic.presentation.printlabel.PrintLabelFragmentViewModel
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.test_print_fragment.*

class PrintLabelFragment: BaseFragment() {

    override val layoutId = R.layout.test_print_fragment

    private lateinit var viewModel: PrintLabelFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        viewModel = viewModel {
            onEvent(getMessageData(), ::handleMessage)
            onEvent(getFailureData(), ::handleFailure)
            onEvent(getProgressData(), ::handleProgress)
            onEvent(getShowChoicePrinterDialog(), ::handleShowChoicePrinterDialog)
            onEvent(getShowPrintLabelDialog(), ::handleShowPrintLabelDialog)
            onEvent(getListPrintLabelLiveData(), ::handleGetListPrintLabelLiveData)
        }
    }

    private fun handleGetListPrintLabelLiveData(list: List<PrintLabelEntity>?) {
        tvMessage.text = list?.map { "${it.datePrint?.toSimpleDateTime()?:""} ${it.product.name} ${it.countLabel}" }?.joinToString("\n")?:""
    }

    private fun handleShowPrintLabelDialog(show: Boolean?) {
        activity!!.supportFragmentManager.showDialog(
            show = show?:false,
            tag = PrintLabelDialogFragment.TAG,
            dialogInstance = {
                PrintLabelDialogFragment()
            }
        )
    }


    private fun handleShowChoicePrinterDialog(show: Boolean?) {
        activity!!.supportFragmentManager.showDialog(
            show = show?:false,
            tag = ChoicePrinterDialogFragment.TAG,
            dialogInstance = {
                ChoicePrinterDialogFragment()
            }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btBarcode.setOnClickListener {
           addMessage( "${(10..99).random()}6546546515")
        }

        btOpenPrinterDialog.setOnClickListener {
           viewModel.openPrinterDialog()
        }

        btOpenPrintLabelDialog.setOnClickListener {
            viewModel.openPrintLabelDialog()
        }

        (activity as PrintActivity).getBarcodeData().observe(viewLifecycleOwner, Observer {
            viewModel.readBarcode(it)
        })

    }


    private fun addMessage(str:String){
        tvMessage.text = "$str \n ${tvMessage.text}"
    }

}