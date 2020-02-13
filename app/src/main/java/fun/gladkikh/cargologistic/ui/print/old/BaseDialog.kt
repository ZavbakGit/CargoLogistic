package `fun`.gladkikh.cargologistic.ui.print.old

import `fun`.gladkikh.cargologistic.common.ui.BaseHostActivity
import `fun`.gladkikh.cargologistic.common.ui.base
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject

abstract class BaseDialog :DialogFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    inline fun base(block: BaseHostActivity.() -> Unit) {
        activity.base(block)
    }


    inline fun <reified T : ViewModel> viewModel(body: T.() -> Unit): T {
        val vm = ViewModelProviders.of(requireActivity(), viewModelFactory)[T::class.java]
        vm.body()
        return vm
    }
}