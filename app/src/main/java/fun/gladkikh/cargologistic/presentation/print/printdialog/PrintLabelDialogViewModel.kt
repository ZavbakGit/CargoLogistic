package `fun`.gladkikh.cargologistic.presentation.print.printdialog


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class PrintLabelDialogViewModel @Inject constructor(): ViewModel(){

    private val state = MutableLiveData<StatePrintLabelDialog>()
    fun getState():LiveData<StatePrintLabelDialog> = state

    fun setState(state: StatePrintLabelDialog?){
        this.state.postValue(state)
    }

}

