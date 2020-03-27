package `fun`.gladkikh.cargologistic.ui.printlabel

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.BaseFragment
import `fun`.gladkikh.cargologistic.common.ui.MyListAdapter
import `fun`.gladkikh.cargologistic.common.ui.ext.onEvent
import `fun`.gladkikh.cargologistic.common.ui.ext.showDialog
import `fun`.gladkikh.cargologistic.common.utils.toSimpleDate
import `fun`.gladkikh.cargologistic.common.utils.toSimpleDateTime
import `fun`.gladkikh.cargologistic.domain.entity.PrintLabelEntity
import `fun`.gladkikh.cargologistic.presentation.printlabel.PrintLabelFragmentViewModel
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.test_print_fragment.*

class PrintLabelFragment : BaseFragment() {

    //override val layoutId = R.layout.test_print_fragment
    override val layoutId = R.layout.print_list_fragment
    private lateinit var viewModel: PrintLabelFragmentViewModel

    private lateinit var adapter: Adapter

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
//        tvMessage.text =
//            list?.map { "${it.datePrint?.toSimpleDateTime() ?: ""} ${it.product.name} ${it.countLabel}" }
//                ?.joinToString("\n") ?: ""
        renderList(list ?: listOf())

    }

    private fun initPrintList() {
        adapter = Adapter(activity as Context)
        print_list.adapter = adapter

        print_list.setOnItemClickListener { parent, view, position, id ->
            viewModel.copyLabel(adapter.getItem(position))
        }
    }

    private fun handleShowPrintLabelDialog(show: Boolean?) {
        activity!!.supportFragmentManager.showDialog(
            show = show ?: false,
            tag = PrintLabelDialogFragment.TAG,
            dialogInstance = {
                PrintLabelDialogFragment()
            }
        )
    }


    private fun handleShowChoicePrinterDialog(show: Boolean?) {
        activity!!.supportFragmentManager.showDialog(
            show = show ?: false,
            tag = ChoicePrinterDialogFragment.TAG,
            dialogInstance = {
                ChoicePrinterDialogFragment()
            }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initPrintList()

//        btBarcode.setOnClickListener {
//            addMessage("${(10..99).random()}6546546515")
//        }
//
//        btOpenPrinterDialog.setOnClickListener {
//            viewModel.openPrinterDialog()
//        }
//
//        btOpenPrintLabelDialog.setOnClickListener {
//            viewModel.openPrintLabelDialog()
//        }

        (activity as PrintActivity).getBarcodeData().observe(viewLifecycleOwner, Observer {
            viewModel.readBarcode(it)
        })

    }

    private fun renderList(list: List<PrintLabelEntity>) {
        adapter.list = list
    }

    private class Adapter(mContext: Context) : MyListAdapter<PrintLabelEntity>(mContext) {
        override fun bindView(item: PrintLabelEntity, holder: Any) {
            holder as ViewHolder
            holder.tvPrintProduct.text = item.product.name
            holder.tvPrintPrinter.text = item.printer.name
            holder.tvPrintCountLabel.text = "Количество " + item.countLabel.toString()
            holder.tvPrintDateCreate.text = item.dateCreate.toSimpleDate()
            holder.tvPrintDatePrint.text = item.datePrint?.toSimpleDateTime() ?: ""
        }

        override fun getLayout(): Int = R.layout.print_list_item
        override fun createViewHolder(view: View): Any =
            ViewHolder(
                view
            )
    }

    private class ViewHolder(view: View) {

        val tvPrintProduct: TextView = view.findViewById(R.id.tvPrintProduct)
        val tvPrintPrinter: TextView = view.findViewById(R.id.tvPrintPrinter)
        val tvPrintCountLabel: TextView = view.findViewById(R.id.tvPrintCountLabel)
        val tvPrintDateCreate: TextView = view.findViewById(R.id.tvPrintDateCreate)
        val tvPrintDatePrint: TextView = view.findViewById(R.id.tvPrintDatePrint)

    }


    private fun addMessage(str: String) {
        //tvMessage.text = "$str \n ${tvMessage.text}"
    }

}