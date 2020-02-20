package `fun`.gladkikh.cargologistic.ui.print

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.type.SingleLiveEvent
import `fun`.gladkikh.cargologistic.common.ui.BaseHostActivity
import `fun`.gladkikh.cargologistic.common.ui.ext.onEvent
import `fun`.gladkikh.cargologistic.domain.entity.SettingsEntity
import `fun`.gladkikh.cargologistic.presentation.print.PrintActivityViewModel
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.gladkikh.mylibrary.BarcodeHelper
import kotlinx.android.synthetic.main.progress_overlay.*


class PrintActivity : BaseHostActivity() {

    override val layoutId = R.layout.print_host_activity
    lateinit var viewModel: PrintActivityViewModel

    private var barcodeHelper: BarcodeHelper? = null
    private val barcodeData = SingleLiveEvent<String>()
    private val barcodeObserver = Observer<String> {
        if (progressView.visibility != View.VISIBLE) {
            barcodeData.postValue(it)
        }
    }
    fun getBarcodeData(): LiveData<String> = barcodeData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {
            onEvent(getMessageData(), ::handleMessage)
            onEvent(getFailureData(), ::handleFailure)
            onEvent(getProgressData(), ::handleProgressBar)

            onEvent(getSettingsData(), {
                if (it != null){
                    refreshSetting(it)
                }
            })
        }
    }

    private fun refreshSetting(settingsEntity: SettingsEntity) {

        barcodeHelper?.getBarcodeLiveData()?.removeObserver(barcodeObserver)

        barcodeHelper = BarcodeHelper(
            this,
            BarcodeHelper.TYPE_TSD.getTypeTSD(settingsEntity.pdt)
        )
        barcodeHelper?.getBarcodeLiveData()?.observe(this, barcodeObserver)
    }
}
