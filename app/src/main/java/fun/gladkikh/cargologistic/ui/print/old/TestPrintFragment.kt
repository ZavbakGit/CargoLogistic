package `fun`.gladkikh.cargologistic.ui.print.old

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.BaseFragment
import `fun`.gladkikh.cargologistic.common.ui.ext.onEvent

import `fun`.gladkikh.cargologistic.presentation.print.old.TestPrintFragmentViewModel
import android.os.Bundle
import kotlinx.android.synthetic.main.test_print_fragment.*

class TestPrintFragment : BaseFragment() {
    override val layoutId = R.layout.test_print_fragment

    private lateinit var viewModel: TestPrintFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {
            onEvent(getMessageData(), ::handleMessage)
            onEvent(getFailureData(), ::handleFailure)
            onEvent(getProgressData(), ::handleProgress)
            //onEvent(getStateDialog(), ::handleStateDialog)
        }
    }

//    private fun handleStateDialog(statePrintDialog: StatePrintDialog?) {
//        if (statePrintDialog?.isOpen == true) {
//            val dialog = activity!!.supportFragmentManager.findFragmentByTag(PrintDialogOld.TAG)
//            if (dialog == null) {
//                PrintDialogOld.newInstance().show(
//                    activity!!.supportFragmentManager,
//                    PrintDialogOld.TAG
//                )
//            }
//        }
//    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        btLongOperation.setOnClickListener {
//            viewModel.executeLongOperation()
//        }
//
//        btOpenDialog.setOnClickListener {
//            viewModel.openDialog()
//        }
//    }

}