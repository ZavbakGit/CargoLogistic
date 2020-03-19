package `fun`.gladkikh.cargologistic.presentation.test

import `fun`.gladkikh.cargologistic.common.presentation.BaseFragmentViewModel
import `fun`.gladkikh.cargologistic.common.type.*
import `fun`.gladkikh.cargologistic.common.ui.dialog.DialogMVM
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity
import `fun`.gladkikh.cargologistic.domain.usecase.ApplySettingsUseCase
import `fun`.gladkikh.cargologistic.domain.usecase.GetProductByBarcodeUseCase
import `fun`.gladkikh.cargologistic.domain.usecase.TestLongUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import java.util.*
import javax.inject.Inject

class TestFragmentViewModel @Inject constructor(
    private val getProductByGuidUseCase: GetProductByBarcodeUseCase,
    private val applySettingsUseCase: ApplySettingsUseCase,
    private val longUseCase: TestLongUseCase
) : BaseFragmentViewModel() {

    init {
        applySetting()
    }


    val showDialog = SingleLiveEvent<Boolean>()
    val showDialog3 = SingleLiveEvent<Boolean>()

    val testDialog2Contract = object : DialogMVM.DialogViewModel<String,String>(){
        init {
            setState("Диалог: ${Date()}")
        }

        override fun onCancel() {
            super.onCancel()
            updateTextData("Cancel1")
        }

        override fun onResult(result: String) {
            super.onResult(result)
            updateTextData(result )
            //setState("Диалог: ${Date()}")
            //closeDialog()
            showDialog.postValue(false)
        }
    }

    val testDialog3Contract = object : DialogMVM.DialogViewModel<String,String>(){
        init {
            setState("Диалог: ${Date()}")
        }

        override fun onCancel() {
            super.onCancel()
            updateTextData("Cancel1")
        }

        override fun onResult(result: String) {
            super.onResult(result)
            updateTextData(result )
            setState("Диалог: ${Date()}")
            showDialog3.postValue(false)
        }
    }

    private val textData = MutableLiveData<String>()
    fun getTextData(): LiveData<String> = textData
    private fun updateTextData(str: String) {
        textData.postValue(str)
    }

    fun getProductByBarcode(barcode: String) {
        updateProgress(Progress(true, "Получаем данные!"))
        getProductByGuidUseCase(barcode, viewModelScope) {
            updateProgress(Progress(false))
            it.either(::handleTextError, ::handleGetProductByBarcode)
        }
    }

    fun printLabel() {

    }

    private fun handlePrintLabel(date: Date) {
        updateTextData("Печать этикетки: " + date.toString())
    }

    private fun handleGetProductByBarcode(productEntity: ProductEntity) {
        updateTextData(productEntity.toString())
    }

    private fun handleTextError(failure: Failure) {
        when (failure) {
            is ErrorDescriptionFailure -> {
                updateTextData(failure.errorDescriptionEntity.toString())
            }
            else -> {
                updateTextData(failure.toString() + ": " + failure.message)
            }
        }


    }

    private fun applySetting() {
        applySettingsUseCase(None(), viewModelScope) { either ->
            either.either(::handleErrorViewModel) { }
        }
    }

    fun executeLongOperation() {
        updateProgress(Progress(true, "Получаем данные!"))
        longUseCase(5, viewModelScope) {
            updateProgress(Progress(false))
            it.either(::updateFailure, {
                updateTextData("Выполнили!")
            })
        }
    }

    fun showDialog(){
        showDialog.postValue(true)
    }

    fun showDialog3(){
        showDialog3.postValue(true)
    }

}


