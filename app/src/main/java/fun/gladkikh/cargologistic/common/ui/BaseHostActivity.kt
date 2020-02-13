package `fun`.gladkikh.cargologistic.common.ui

import `fun`.gladkikh.cargologistic.R
import `fun`.gladkikh.cargologistic.common.type.Progress
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.progress_overlay.*
import javax.inject.Inject

abstract class BaseHostActivity : AppCompatActivity() {

    private val contentId = R.layout.host_activity


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupContent()
    }

    open fun setupContent() {
        setContentView(contentId)
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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