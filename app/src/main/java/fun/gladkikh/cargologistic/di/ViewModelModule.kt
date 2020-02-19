package `fun`.gladkikh.cargologistic.di
import `fun`.gladkikh.cargologistic.common.presentation.ViewModelFactory
import `fun`.gladkikh.cargologistic.presentation.login.LoginActivityViewModel
import `fun`.gladkikh.cargologistic.presentation.login.LoginFragmentViewModel
import `fun`.gladkikh.cargologistic.presentation.main.MainActivityViewModel
import `fun`.gladkikh.cargologistic.presentation.main.MainFragmentViewModel
import `fun`.gladkikh.cargologistic.presentation.print.PrintActivityViewModel
import `fun`.gladkikh.cargologistic.presentation.print.PrintFragmentViewModel
import `fun`.gladkikh.cargologistic.presentation.setting.SettingActivityViewModel
import `fun`.gladkikh.cargologistic.presentation.setting.SettingFragmentViewModel
import `fun`.gladkikh.cargologistic.presentation.test.TestActivityViewModel
import `fun`.gladkikh.cargologistic.presentation.test.TestFragmentViewModel
import `fun`.gladkikh.cargologistic.ui.main.MainActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PrintActivityViewModel::class)
    abstract fun bindPrintActivityViewModel(viewModel: PrintActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PrintFragmentViewModel::class)
    abstract fun bindPrintLabelDialodViewModel(viewModel: PrintFragmentViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(LoginActivityViewModel::class)
    abstract fun bindLoginActivityViewModel(viewModel: LoginActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginFragmentViewModel::class)
    abstract fun bindLoginFragmentViewModel(viewModel: LoginFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingActivityViewModel::class)
    abstract fun bindSettingActivityViewModel(viewModel: SettingActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingFragmentViewModel::class)
    abstract fun bindSettingFragmentViewModel(viewModel: SettingFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    abstract fun bindMainFragmentViewModel(viewModel: MainFragmentViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(TestActivityViewModel::class)
    abstract fun bindTestActivityViewModel(viewModel: TestActivityViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(TestFragmentViewModel::class)
    abstract fun bindTestFragmentViewModel(viewModel: TestFragmentViewModel): ViewModel



}
