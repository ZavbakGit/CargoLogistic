package `fun`.gladkikh.cargologistic.ui.test

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.BaseFragment
import `fun`.gladkikh.cargologistic.common.ui.ext.onEvent
import `fun`.gladkikh.cargologistic.presentation.test.TestFragmentViewModel
import android.os.Bundle
import kotlinx.android.synthetic.main.test_fragment.*

class TestFragment : BaseFragment() {
    override val layoutId = R.layout.test_fragment

    private lateinit var viewModel: TestFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {
            onEvent(getMessageData(), ::handleMessage)
            onEvent(getFailureData(), ::handleFailure)
            onEvent(getProgressData(), ::handleProgress)

            onEvent(getTextData(),
                {
                    tvMessage.text = (it ?: "Пусто") + "*\n \n " + tvMessage.text.toString()
                })

        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btTestGetProductByBarcode.setOnClickListener {
            viewModel.getProductByBarcode("2000000000916")
        }

        btPrintLabel.setOnClickListener {
            viewModel.printLabel()
        }
    }
}