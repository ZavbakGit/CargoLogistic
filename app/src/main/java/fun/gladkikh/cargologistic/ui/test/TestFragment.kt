package `fun`.gladkikh.cargologistic.ui.test

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.BaseFragment
import `fun`.gladkikh.cargologistic.common.ui.ext.onEvent
import `fun`.gladkikh.cargologistic.presentation.test.TestFragmentViewModel
import android.os.Bundle
import androidx.lifecycle.Observer
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


            onEvent(showDialog,{
                var dialog = (activity!!.supportFragmentManager.findFragmentByTag(TestDialog2.TAG) as? TestDialog2)
                if (dialog == null){
                    dialog = TestDialog2()
                }

                if (it == true){
                    if (!dialog.isVisible){
                        dialog.show(activity!!.supportFragmentManager,TestDialog2.TAG)
                    }
                }else{
                    if (dialog.isVisible){
                        dialog.dismiss()
                    }
                }
            })

            onEvent(showDialog3,{
                var dialog3 = (activity!!.supportFragmentManager.findFragmentByTag("Test3") as? TestDialog3)
                if (dialog3 == null){
                    dialog3 = TestDialog3()
                }

                if (it == true){
                    if (!dialog3.isVisible){
                        dialog3.show(activity!!.supportFragmentManager,"Test3")
                    }
                }else{
                    if (dialog3.isVisible){
                        dialog3.dismiss()
                    }
                }
            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as TestActivity).getBarcodeData().observe(viewLifecycleOwner, Observer {
            tvMessage.text = (it ?: "Пусто") + "*\n \n " + tvMessage.text.toString()
        })

        btTestGetProductByBarcode.setOnClickListener {
            viewModel.getProductByBarcode("2000000000916")
        }

        btPrintLabel.setOnClickListener {
            viewModel.printLabel()
        }

        btLongOperation.setOnClickListener {
            viewModel.executeLongOperation()
        }

        btTestDialog.setOnClickListener {
            viewModel.showDialog()
        }


        btTestDialog3.setOnClickListener {
            viewModel.showDialog3()
        }
    }
}