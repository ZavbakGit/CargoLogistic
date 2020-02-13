package `fun`.gladkikh.cargologistic.ui.print.old

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class DialogControl <T, R> @Inject constructor():ViewModel(){

    private val isShow = MutableLiveData<Boolean>()
    private val data = MutableLiveData<T>()
    private val result = MutableLiveData<R>()

    fun getData():LiveData<T> = data
    fun getResult():LiveData<R> = result
    fun getIsShow():LiveData<Boolean> = isShow

    fun setData(data:T){
        this.data.postValue(data)
    }

    fun setResult(result:R){
        this.result.postValue(result)
        isShow.postValue(false)
    }


}