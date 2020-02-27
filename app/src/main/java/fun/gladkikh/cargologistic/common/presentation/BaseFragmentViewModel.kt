package `fun`.gladkikh.cargologistic.common.presentation

import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.Message
import `fun`.gladkikh.cargologistic.common.type.Progress
import `fun`.gladkikh.cargologistic.common.type.SingleLiveEvent
import `fun`.gladkikh.cargologistic.ui.common.Command
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseFragmentViewModel:ViewModel(){
    private val failureData = SingleLiveEvent<Failure>()
    private val messageData = SingleLiveEvent<Message>()
    private val progressData = MutableLiveData<Progress>()
    private val commandData = SingleLiveEvent<Command>()

    fun getFailureData():LiveData<Failure> = failureData
    fun getMessageData():LiveData<Message> = messageData
    fun getProgressData():LiveData<Progress> = progressData
    fun getCommandData():LiveData<Command> = commandData


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

    fun updateCommand(command: Command){
        this.commandData.postValue(command)
    }


    fun handleMessageViewModel(message: Message) {
        updateMessage(message)
    }

    fun handleErrorViewModel(failure: Failure) {
        updateFailure(failure)
    }

    override fun onCleared() {
        super.onCleared()
        progressData.postValue(Progress(false))
    }
}