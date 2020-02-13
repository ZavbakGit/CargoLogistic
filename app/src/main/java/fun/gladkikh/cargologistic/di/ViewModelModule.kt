package `fun`.gladkikh.cargologistic.di
import `fun`.gladkikh.cargologistic.common.presentation.ViewModelFactory
import `fun`.gladkikh.cargologistic.presentation.print.PrintActivityViewModel
import `fun`.gladkikh.cargologistic.presentation.print.PrintFragmentViewModel
import `fun`.gladkikh.cargologistic.presentation.print.old.TestPrintFragmentViewModel
import `fun`.gladkikh.cargologistic.presentation.print.printdialog.PrintLabelDialogViewModel
import `fun`.gladkikh.cargologistic.presentation.print.printerdialog.PrinterDialogViewModel
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
    @ViewModelKey(PrintLabelDialogViewModel::class)
    abstract fun bindPrintFragmentViewModel(viewModel: PrintLabelDialogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PrintFragmentViewModel::class)
    abstract fun bindPrintLabelDialodViewModel(viewModel: PrintFragmentViewModel): ViewModel



    @Binds
    @IntoMap
    @ViewModelKey(PrinterDialogViewModel::class)
    abstract fun bindPrinterDialogViewModel(viewModel: PrinterDialogViewModel): ViewModel



}
