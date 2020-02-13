package `fun`.gladkikh.cargologistic.ui.main

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.ui.BaseFragment
import `fun`.gladkikh.cargologistic.common.ui.ext.onEvent
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity
import `fun`.gladkikh.cargologistic.presentation.main.MainFragmentViewModel
import android.os.Bundle
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.tvName
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : BaseFragment() {
    override val layoutId = R.layout.main_fragment

    private lateinit var viewModel: MainFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {
            onEvent(getMessageData(), ::handleMessage)
            onEvent(getFailureData(), ::handleFailure)
            onEvent(getProgressData(), ::handleProgress)
            onEvent(getCurrentAccountData(), ::handleCurrentAccount)
        }

    }

    private fun handleCurrentAccount(accountEntity: AccountEntity?) {
        tvName.text = accountEntity?.user ?: "Не авторизован..."
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btPrint.setOnClickListener {
            base {
                navController.navigate(R.id.printActivity)
            }
        }
    }
}