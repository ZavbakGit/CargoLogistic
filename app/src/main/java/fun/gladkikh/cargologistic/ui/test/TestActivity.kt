package `fun`.gladkikh.cargologistic.ui.test

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.BaseHostActivity
import `fun`.gladkikh.cargologistic.presentation.test.TestActivityViewModel
import android.os.Bundle


class TestActivity : BaseHostActivity() {

    override val layoutId = R.layout.test_host_activity

    lateinit var viewModel: TestActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {

        }
    }



}
