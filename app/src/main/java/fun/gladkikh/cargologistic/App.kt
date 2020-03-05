package `fun`.gladkikh.cargologistic

import `fun`.gladkikh.cargologistic.di.AppModule
import `fun`.gladkikh.cargologistic.di.RepositoryModule
import `fun`.gladkikh.cargologistic.di.ServicesModule
import `fun`.gladkikh.cargologistic.di.ViewModelModule
import `fun`.gladkikh.cargologistic.domain.entity.AccountEntity
import `fun`.gladkikh.cargologistic.domain.entity.SettingsEntity
import `fun`.gladkikh.cargologistic.remote.RequestRemote
import `fun`.gladkikh.cargologistic.remote.RequestRemoteImpl
import `fun`.gladkikh.cargologistic.ui.login.LoginActivity
import `fun`.gladkikh.cargologistic.ui.login.LoginFragment
import `fun`.gladkikh.cargologistic.ui.main.MainActivity
import `fun`.gladkikh.cargologistic.ui.main.MainFragment
import `fun`.gladkikh.cargologistic.ui.print.PrintActivity
import `fun`.gladkikh.cargologistic.ui.print.PrintFragment
import `fun`.gladkikh.cargologistic.ui.print.PrintLabelDialogFragment
import `fun`.gladkikh.cargologistic.ui.print.PrinterDialogFragment
import `fun`.gladkikh.cargologistic.ui.settings.SettingsActivity
import `fun`.gladkikh.cargologistic.ui.settings.SettingsFragment
import `fun`.gladkikh.cargologistic.ui.test.TestActivity
import `fun`.gladkikh.cargologistic.ui.test.TestFragment
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
        var requestRemote: RequestRemote? = null
            private set
        var accountEntity: AccountEntity? = null
            private set

        fun appContext(): Context? = instance?.applicationContext

        fun initRequestRemote(settingsEntity: SettingsEntity) {
            requestRemote = RequestRemoteImpl(
                context =instance!!,
                isDebug = Constants.IS_TEST_BUILD,
                baseUrl = settingsEntity.host?:"",
                password = settingsEntity.password1C?:"",
                user = settingsEntity.login1C?:"")
        }

        fun initAccount(accountEntity: AccountEntity) {
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
    fun inject(printFragment: PrintFragment)
    fun inject(printLabelDialogFragment: PrintLabelDialogFragment)
    fun inject(printerDialogFragment: PrinterDialogFragment)
    fun inject(loginActivity: LoginActivity)
    fun inject(loginFragment: LoginFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(settingsActivity: SettingsActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(testActivity: TestActivity)
    fun inject(testFragment: TestFragment)

}