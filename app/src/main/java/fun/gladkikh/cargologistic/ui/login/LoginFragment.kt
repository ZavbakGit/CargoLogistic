package `fun`.gladkikh.cargologistic.ui.login

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.Constants
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.BaseFragment
import `fun`.gladkikh.cargologistic.common.ui.ext.onEvent
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity
import `fun`.gladkikh.cargologistic.presentation.login.LoginFragmentViewModel
import `fun`.gladkikh.cargologistic.ui.common.Command
import `fun`.gladkikh.cargologistic.ui.common.OpenFormCommand
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : BaseFragment() {
    override val layoutId = R.layout.login_fragment

    private lateinit var viewModel: LoginFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {
            onEvent(getMessageData(), ::handleMessage)
            onEvent(getFailureData(), ::handleFailure)
            onEvent(getProgressData(), ::handleProgress)
            onEvent(getCurrentAccountData(), ::handleCurrentAccount)
            onEvent(getCommandData(), ::handleCommand)
        }

    }

    private fun handleCommand(command: Command?) {
        when (command) {
            is OpenFormCommand -> {
                when (command.id) {
                    Constants.COMMAND_OPEN_MAIN -> {
                        base {
                            navController.navigate(R.id.mainActivity)
                        }
                    }
                }
            }
        }
    }

    private fun handleCurrentAccount(accountEntity: AccountEntity?) {
        tvName.text = accountEntity?.user ?: "Не авторизован..."
        edPassword.setText("")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (!Constants.IS_TEST_BUILD) {
            btSaveTestSettings.visibility = View.GONE
        }

        btLogin.setOnClickListener {
            viewModel.login(edPassword.text.toString())
        }

        btExit.setOnClickListener {
            viewModel.unLogin()
        }


        btSaveTestSettings.setOnClickListener {
            viewModel.saveTestSettings()
        }

        btTest.setOnClickListener {
            viewModel.testRemote()
        }

        btSettings.setOnClickListener {
            base {
                navController.navigate(R.id.settingsActivity)
            }
        }
    }
}