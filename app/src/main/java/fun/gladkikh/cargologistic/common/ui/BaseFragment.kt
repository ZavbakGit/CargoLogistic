package `fun`.gladkikh.cargologistic.common.ui

import `fun`.gladkikh.cargologistic.common.type.ErrorDescriptionFailure
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.Message
import `fun`.gladkikh.cargologistic.common.type.Progress
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutId, container, false)
    }

    fun handleMessage(message: Message?) = base { showMessage(message?.message ?: "") }
    fun handleFailure(failure: Failure?) = base {

        when(failure){
            is ErrorDescriptionFailure ->{
                showMessage(failure.errorDescriptionEntity.description)
            }
            else ->{
                showMessage(failure?.message ?: "")
            }
        }


    }
    fun handleProgress(progress: Progress?) = base { handleProgressBar(progress) }

    inline fun base(block: BaseHostActivity.() -> Unit) {
        activity.base(block)
    }


    inline fun <reified T : ViewModel> viewModel(body: T.() -> Unit): T {
        //val vm = ViewModelProviders.of(this, viewModelFactory)[T::class.java]
        val vm = ViewModelProviders.of(requireActivity(), viewModelFactory)[T::class.java]
        vm.body()
        return vm
    }

    fun close() = fragmentManager?.popBackStack()
}