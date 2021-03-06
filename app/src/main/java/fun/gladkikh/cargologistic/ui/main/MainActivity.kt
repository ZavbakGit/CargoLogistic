package `fun`.gladkikh.cargologistic.ui.main

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.BaseHostActivity
import `fun`.gladkikh.cargologistic.presentation.login.LoginActivityViewModel
import android.os.Bundle
import androidx.navigation.Navigation

class MainActivity : BaseHostActivity() {

    override val layoutId = R.layout.main_host_activity

    lateinit var viewModel: LoginActivityViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        viewModel = viewModel {

        }
    }
}
