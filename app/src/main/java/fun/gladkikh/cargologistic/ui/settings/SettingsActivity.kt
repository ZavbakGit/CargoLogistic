package `fun`.gladkikh.cargologistic.ui.settings

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.BaseHostActivity
import `fun`.gladkikh.cargologistic.presentation.login.LoginActivityViewModel
import `fun`.gladkikh.cargologistic.presentation.setting.SettingActivityViewModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation

class SettingsActivity : BaseHostActivity() {
    override val layoutId = R.layout.setting_host_activity

    lateinit var viewModel: SettingActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        viewModel = viewModel {

        }
    }
}