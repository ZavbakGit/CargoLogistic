package `fun`.gladkikh.cargologistic.di
import `fun`.gladkikh.cargologistic.common.presentation.ViewModelFactory
import `fun`.gladkikh.cargologistic.presentation.print.PrintActivityViewModel
import `fun`.gladkikh.cargologistic.presentation.print.PrintFragmentViewModel
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
    abstract fun bindTestViewModel(viewModel: PrintActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PrintFragmentViewModel::class)
    abstract fun bindPrintLabelDialodViewModel(viewModel: PrintFragmentViewModel): ViewModel


}
