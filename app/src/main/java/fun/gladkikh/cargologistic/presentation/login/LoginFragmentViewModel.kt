package `fun`.gladkikh.cargologistic.presentation.login

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.Constants
import `fun`.gladkikh.cargologistic.common.presentation.BaseFragmentViewModel
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.Message
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.common.type.Progress
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity
import `fun`.gladkikh.cargologistic.domain.entity.SettingsEntity
import `fun`.gladkikh.cargologistic.domain.usecase.*
import `fun`.gladkikh.cargologistic.ui.common.OpenFormCommand
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import java.util.*
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor(
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val testRemoteRequestUseCase: TestRemoteRequestUseCase,
    private val applySettingsUseCase: ApplySettingsUseCase,
    private val loginAccountUseCase: LoginAccountUseCase,
    private val removeAccountUseCase: RemoveAccountUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : BaseFragmentViewModel() {

    private val currentAccountData = MutableLiveData<AccountEntity>()
    fun getCurrentAccountData(): LiveData<AccountEntity> = currentAccountData
    private fun updateCurrentAccountData(accountEntity: AccountEntity?) {
        App.initAccount(accountEntity)
        currentAccountData.postValue(accountEntity)
        if (App.accountEntity?.user != null){
            updateCommand(OpenFormCommand(Constants.COMMAND_OPEN_MAIN))
        }
    }

    init {
        applySetting()
        getCurrentAccount()
    }

    /**
     * Читаем и применяем настройки
     */
    private fun applySetting() {
        applySettingsUseCase(None(), viewModelScope) { either ->
            either.either(::handleErrorViewModel) { }
        }
    }

    /**
     * Читаем и применяем Аккаунт
     */
    private fun getCurrentAccount() {
      getAccountUseCase(None(), viewModelScope) {
            it.either(::handleErrorGetCurrentAccount, ::handleGetCurrentAccount)
        }
    }

    /**
     * Логинемся
     */
    fun login(password: String) {
        updateProgress(Progress(true, "Login..."))
        loginAccountUseCase(password, viewModelScope) {
            it.either(::handleErrorLogin, ::handleLogin)
            updateProgress(Progress(false))
        }
    }

    /**
     * Разлогиневаемся
     */
    fun unLogin() {
        updateProgress(Progress(true, "Exit.."))
        removeAccountUseCase(None(), viewModelScope) {
            it.either(::handleErrorViewModel, ::handleGetCurrentAccount)
            updateProgress(Progress(false))
        }
    }

    /**
     * Сохраняем тестовые настройки
     */
    fun saveTestSettings() {
        val settings = SettingsEntity(
            login1C = "Админ",
            password1C = "123",
            host = "http://172.31.255.150/UT/hs/api/",
            date = Date(),
            pdt = 1
        )

        updateProgress(Progress(true, "Сохраняем!"))
        saveSettingsUseCase(settings, viewModelScope) { either ->
            either.either(::handleErrorViewModel) {
                handleMessageViewModel(Message("Сохранили!"))
            }
            updateProgress(Progress(false))
        }

        App.initRequestRemote(settings)
    }

    /**
     * Тестируем подключение
     */
    fun testRemote() {
        updateProgress(Progress(true, "Соеденяемся...!"))
        testRemoteRequestUseCase(None(), viewModelScope) { either ->
            either.either(::handleErrorViewModel) {
                handleMessageViewModel(Message(it))
            }
            updateProgress(Progress(false))
        }
    }

    private fun handleGetCurrentAccount(accountEntity: AccountEntity) {
        updateCurrentAccountData(accountEntity)
    }
    private fun handleErrorGetCurrentAccount(failure: Failure) {
        updateCurrentAccountData(null)
    }
    private fun handleLogin(accountEntity: AccountEntity) {
        updateCurrentAccountData(accountEntity)
    }
    private fun handleErrorLogin(failure: Failure) {
        handleErrorViewModel(failure)
        updateCurrentAccountData(null)
    }


}


