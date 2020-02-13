package `fun`.gladkikh.cargologistic

import `fun`.gladkikh.cargologistic.di.AppModule
import `fun`.gladkikh.cargologistic.di.ViewModelModule
import `fun`.gladkikh.cargologistic.ui.ListPrinterDialog
import `fun`.gladkikh.cargologistic.ui.print.PrintActivity
import `fun`.gladkikh.cargologistic.ui.print.PrintFragment
import `fun`.gladkikh.cargologistic.ui.print.PrintLabelDialogFragment
import `fun`.gladkikh.cargologistic.ui.print.PrinterDialogFragment
import `fun`.gladkikh.cargologistic.ui.print.old.PrintDialogOld
import `fun`.gladkikh.cargologistic.ui.print.old.TestPrintFragment
import android.app.Application
import android.content.Context
import dagger.Component
import javax.inject.Singleton

class App : Application() {

    init {
        instance = this
    }

    companion object {
        lateinit var appComponent: AppComponent
        var instance: App? = null
        fun appContext(): Context? = instance?.applicationContext


    }

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }



    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}


@Singleton
@Component(
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {
    fun inject(mainActivity: PrintActivity)
    fun inject(mainActivity: TestPrintFragment)
    fun inject(printDialog: PrintDialogOld)
    fun inject(listPrinterDialog: ListPrinterDialog)
    fun inject(printFragment: PrintFragment)
    fun inject(printLabelDialogFragment: PrintLabelDialogFragment)
    fun inject(printerDialogFragment: PrinterDialogFragment)
}