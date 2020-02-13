package `fun`.gladkikh.cargologistic.ui.print

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.BaseHostActivity
import `fun`.gladkikh.cargologistic.presentation.print.PrintActivityViewModel
import android.os.Bundle


class PrintActivity : BaseHostActivity() {

    override val layoutId = R.layout.print_host_activity

    lateinit var viewModel: PrintActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {

        }
    }



}
