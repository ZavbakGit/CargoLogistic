package `fun`.gladkikh.cargologistic.presentation.test

import `fun`.gladkikh.cargologistic.common.presentation.BaseFragmentViewModel
import `fun`.gladkikh.cargologistic.common.type.ErrorDescriptionFailure
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.common.type.Progress
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity
import `fun`.gladkikh.cargologistic.domain.usecase.ApplySettingsUseCase
import `fun`.gladkikh.cargologistic.domain.usecase.GetProductByGuidUseCase
import `fun`.gladkikh.cargologistic.domain.usecase.PrintLabelUseCase
import `fun`.gladkikh.cargologistic.domain.usecase.TestLongUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import java.util.*
import javax.inject.Inject

class TestFragmentViewModel @Inject constructor(
    private val getProductByGuidUseCase: GetProductByGuidUseCase,
    private val applySettingsUseCase: ApplySettingsUseCase,
    private val printLabelUseCase: PrintLabelUseCase,
    private val longUseCase: TestLongUseCase
) : BaseFragmentViewModel() {

    init {
        applySetting()
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
        updateProgress(Progress(true, "Получаем данные!"))
        val param = PrintLabelUseCase.Params(
            guidProduct = "cbcf493f-55bc-11d9-848a-00112f43529a",
            dataCreate = Date(),
            barcode = "2222",
            count = 2,
            guidPrinter = "833d2000-4ecc-11ea-98bc-902b34ac9554"
        )


        printLabelUseCase(param, viewModelScope) {
            updateProgress(Progress(false))
            it.either(::handleTextError, ::handlePrintLabel)
        }
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


}


