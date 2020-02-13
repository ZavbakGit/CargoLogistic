package `fun`.gladkikh.cargologistic.common.presentation

import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.Message
import `fun`.gladkikh.cargologistic.common.type.Progress
import `fun`.gladkikh.cargologistic.common.type.SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseFragmentViewModel:ViewModel(){
    private var failureData = SingleLiveEvent<Failure>()
    private var messageData = SingleLiveEvent<Message>()
    private var progressData = MutableLiveData<Progress>()


    fun getFailureData():LiveData<Failure> = failureData
    fun getMessageData():LiveData<Message> = messageData
    fun getProgressData():LiveData<Progress> = progressData


    init {
        progressData.postValue(Progress(false))
    }

    fun updateFailure(failure: Failure){
        this.failureData.postValue(failure)
    }

    fun updateMessage(message: Message){
        this.messageData.postValue(message)
    }

    fun updateProgress(progress: Progress){
        this.progressData.postValue(progress)
    }



    override fun onCleared() {
        super.onCleared()
        progressData.postValue(Progress(false))
    }
}