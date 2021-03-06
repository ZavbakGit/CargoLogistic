package `fun`.gladkikh.cargologistic

import `fun`.gladkikh.cargologistic.db.AppDatabase
import `fun`.gladkikh.cargologistic.di.AppModule
import `fun`.gladkikh.cargologistic.di.RepositoryModule
import `fun`.gladkikh.cargologistic.di.ServicesModule
import `fun`.gladkikh.cargologistic.di.ViewModelModule
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity
import `fun`.gladkikh.cargologistic.domain.entity.SettingsEntity
import `fun`.gladkikh.cargologistic.data.RemoteRequest
import `fun`.gladkikh.cargologistic.remote.RequestRemoteImpl
import `fun`.gladkikh.cargologistic.ui.login.LoginActivity
import `fun`.gladkikh.cargologistic.ui.login.LoginFragment
import `fun`.gladkikh.cargologistic.ui.main.MainActivity
import `fun`.gladkikh.cargologistic.ui.main.MainFragment
import `fun`.gladkikh.cargologistic.ui.print.*
import `fun`.gladkikh.cargologistic.ui.printlabel.ChoicePrinterDialogFragment
import `fun`.gladkikh.cargologistic.ui.printlabel.PrintActivity
import `fun`.gladkikh.cargologistic.ui.print.PrinterDialogFragment
import `fun`.gladkikh.cargologistic.ui.printlabel.PrintLabelDialogFragment
import `fun`.gladkikh.cargologistic.ui.printlabel.PrintLabelFragment
import `fun`.gladkikh.cargologistic.ui.settings.SettingsActivity
import `fun`.gladkikh.cargologistic.ui.settings.SettingsFragment
import `fun`.gladkikh.cargologistic.ui.test.TestActivity
import `fun`.gladkikh.cargologistic.ui.test.TestDialog2
import `fun`.gladkikh.cargologistic.ui.test.TestDialog3
import `fun`.gladkikh.cargologistic.ui.test.TestFragment
import android.app.Application
import android.content.Context
import com.google.gson.GsonBuilder
import dagger.Component
import javax.inject.Singleton

class App : Application() {

    init {
        instance = this
    }

    companion object {
        lateinit var appComponent: AppComponent
        var instance: App? = null
        var requestRemote: RemoteRequest? = null
            private set
        var accountEntity: AccountEntity? = null
            private set

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

        fun appContext(): Context? = instance?.applicationContext

        fun initRequestRemote(settingsEntity: SettingsEntity) {
            requestRemote = RequestRemoteImpl(
                context = instance!!,
                isDebug = Constants.IS_TEST_BUILD,
                baseUrl = settingsEntity.host ?: "http://0.0.0.0/",
                password = settingsEntity.password1C ?: "",
                user = settingsEntity.login1C ?: ""
            )
        }

        fun initAccount(accountEntity: AccountEntity?) {
            this.accountEntity = accountEntity
        }
    }

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }


    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        AppDatabase.getInstance(this)
    }
}


@Singleton
@Component(
    modules = [AppModule::class,
        ServicesModule::class,
        RepositoryModule::class,
        ViewModelModule::class]
)
interface AppComponent {
    fun inject(mainActivity: PrintActivity)
    fun inject(printFragment: PrintFragmentOld)
    fun inject(printLabelDialogFragment: PrintLabelDialogFragmentOld)
    fun inject(printerDialogFragment: PrinterDialogFragment)
    fun inject(loginActivity: LoginActivity)
    fun inject(loginFragment: LoginFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(settingsActivity: SettingsActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(testActivity: TestActivity)
    fun inject(testFragment: TestFragment)
    fun inject(testDialog2: TestDialog2)
    fun inject(testDialog3: TestDialog3)
    fun inject(printerDialogFragment1: ChoicePrinterDialogFragmentOld)
    fun inject(printLabelFragment: PrintLabelFragment)
    fun inject(choicePrinterDialogFragment: ChoicePrinterDialogFragment)
    fun inject(printLabelDialogFragment: PrintLabelDialogFragment)
}