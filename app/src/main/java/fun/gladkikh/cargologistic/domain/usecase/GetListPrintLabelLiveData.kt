package `fun`.gladkikh.cargologistic.domain.usecase

import `fun`.gladkikh.cargologistic.domain.repository.AccountRepository
import javax.inject.Inject

class GetListPrintLabelLiveData @Inject constructor(
    private val accountRepository: AccountRepository
){
    fun getListPrintLabel() = accountRepository.getListPrintLabel()
}