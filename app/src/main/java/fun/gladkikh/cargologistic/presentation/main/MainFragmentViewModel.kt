package `fun`.gladkikh.cargologistic.presentation.main


import `fun`.gladkikh.cargologistic.common.presentation.BaseFragmentViewModel
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity
import `fun`.gladkikh.cargologistic.domain.usecase.GetAccountUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase
) : BaseFragmentViewModel() {

    private val currentAccountData = MutableLiveData<AccountEntity>()
    fun getCurrentAccountData(): LiveData<AccountEntity> = currentAccountData
    private fun updateCurrentAccountData(accountEntity: AccountEntity?) {
        currentAccountData.postValue(accountEntity)
    }

    init {
        getCurrentAccount()
    }

    private fun getCurrentAccount() {
        getAccountUseCase(None(), viewModelScope) {
            it.either(::handleErrorLogin, ::handleCurrentAccountViewModel)
        }
    }

    private fun handleCurrentAccountViewModel(accountEntity: AccountEntity) {
        updateCurrentAccountData(accountEntity)
    }

    private fun handleErrorLogin(failure: Failure) {
        handleErrorViewModel(failure)
        updateCurrentAccountData(null)
    }

}