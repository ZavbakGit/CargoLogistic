package `fun`.gladkikh.cargologistic.ui.test

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.dialog.DialogMVVM
import `fun`.gladkikh.cargologistic.presentation.test.TestFragmentViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.test_dialog.*


class TestDialog2 : DialogMVVM<String, String>() {

    companion object{
        const val TAG = "TestDialog2"
    }

    private lateinit var viewModel: TestFragmentViewModel

    private val layoutId = R.layout.test_dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {}
        dialogViewModel = viewModel.testDialog2Contract
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }



    override fun setStateState(state: String?) {
        tvMessage.text = state?:""
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btOk.setOnClickListener {
            sendResult(tvMessage.text.toString())
        }
    }
}