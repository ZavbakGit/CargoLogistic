package `fun`.gladkikh.cargologistic.ui.test

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.dialog.DialogMVM
import `fun`.gladkikh.cargologistic.presentation.test.TestFragmentViewModel
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class TestDialog3 : DialogMVM<String, String>() {

    private lateinit var viewModel: TestFragmentViewModel

    private val layoutId = R.layout.test_dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {}
        dialogViewModel = viewModel.testDialog2Contract
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Выберите принтер")
                .setPositiveButton("Ok"){_,_ ->
                    sendResult("Ok")
                }
                .setNegativeButton("No"){_,_ ->
                    sendResult("No")
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun refreshState(state: String) {
        dialog?.setTitle(state)
    }
}