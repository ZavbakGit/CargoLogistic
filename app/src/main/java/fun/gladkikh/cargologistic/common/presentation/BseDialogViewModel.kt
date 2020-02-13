package `fun`.gladkikh.cargologistic.common.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BseDialogViewModel: ViewModel(){
    private val isOpen = MutableLiveData<Boolean>()
    fun getIsOpen():LiveData<Boolean> = isOpen

    fun setIsOpen(isOpen:Boolean){
        this.isOpen.postValue(isOpen)
    }
}