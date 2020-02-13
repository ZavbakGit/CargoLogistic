package `fun`.gladkikh.cargologistic.presentation.print.old

import `fun`.gladkikh.cargologistic.common.presentation.BaseFragmentViewModel
import `fun`.gladkikh.cargologistic.domain.usecase.TestLongUseCase

import javax.inject.Inject

class TestPrintFragmentViewModel @Inject constructor(
    private val testLong: TestLongUseCase
) : BaseFragmentViewModel(){

//    private val statePrintDialogDate = MutableLiveData<StatePrintDialog>()
//    override fun getStateDialog(): LiveData<StatePrintDialog> = statePrintDialogDate
//    override fun updatePrintStateDialog(statePrintDialog: StatePrintDialog) {
//        statePrintDialogDate.postValue(statePrintDialog)
//        if (!statePrintDialog.isOpen && statePrintDialog.isPositiveEvent){
//            updateMessage(Message("Количество: ${statePrintDialog.count}"))
//        }
//    }
//
//
//    fun executeLongOperation() {
//        updateProgress(Progress(true, "Получаем данные!"))
//        testLong(5, viewModelScope) {
//            updateProgress(Progress(false))
//            it.either(::updateFailure, ::handleLongOperation)
//        }
//    }
//
//    fun openDialog() {
//        updatePrintStateDialog(
//            StatePrintDialog(
//                true,
//                ProductEntity(
//                    guid = "111",
//                    name = "Горошик в банке 200 мл",
//                    barcode = "46546465464"
//                ),
//                1
//            )
//        )
//    }
//
//    private fun handleLongOperation(none: None) {
//        //updateMessage(Message("Выполнили долгую операцию!"))
//        openDialog()
//    }
}