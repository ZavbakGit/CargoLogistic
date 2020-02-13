package `fun`.gladkikh.cargologistic.presentation.print.printerdialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class PrinterDialogViewModel @Inject constructor(): ViewModel(){

    private val state = MutableLiveData<StatePrinterDialog>()
    fun getState():LiveData<StatePrinterDialog> = state

    fun setState(state: StatePrinterDialog){
        this.state.postValue(state)
    }

}

