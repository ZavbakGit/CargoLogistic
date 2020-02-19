package `fun`.gladkikh.cargologistic.common.ui

import `fun`.gladkikh.cargologistic.common.type.ErrorDescriptionFailure
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.Message
import `fun`.gladkikh.cargologistic.common.type.Progress
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import kotlinx.android.synthetic.main.progress_overlay.*
import javax.inject.Inject

abstract class BaseHostActivity : AppCompatActivity() {

    abstract val layoutId: Int

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupContent()
    }

    open fun setupContent() {
        setContentView(layoutId)
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
    fun handleProgressBar(progress: Progress?) {
        if (progress?.isOpen == true) {
            progressView.visibility = View.VISIBLE
            tvMessageProgress.text = progress.message ?: ""
        } else {
            progressView.visibility = View.GONE
            tvMessageProgress.text = ""
        }
    }

    inline fun <reified T : ViewModel> viewModel(body: T.() -> Unit): T {
        val vm = ViewModelProviders.of(this, viewModelFactory)[T::class.java]
        vm.body()
        return vm
    }

}

inline fun Activity?.base(block: BaseHostActivity.() -> Unit) {
    (this as? BaseHostActivity)?.let(block)
}