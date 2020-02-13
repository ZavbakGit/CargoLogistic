package `fun`.gladkikh.cargologistic.ui

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.common.ui.BaseHostActivity
import `fun`.gladkikh.cargologistic.common.ui.base
import `fun`.gladkikh.cargologistic.common.ui.ext.onEvent
import `fun`.gladkikh.cargologistic.presentation.print.old.TestPrintFragmentViewModel
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject

class ListPrinterDialog : DialogFragment() {

    private val catNames = arrayOf("Васька", "Рыжик", "Мурзик")

    companion object {
        const val TAG = "ListPrinterDialog"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: TestPrintFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = viewModel {
//            onEvent(getStateDialog(), {
//
//            })
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val selectedItems = ArrayList<Int>() // Where we track the selected items
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Выберите любимое имя кота")
                .setSingleChoiceItems(catNames, 1
                ) { dialog, item ->
                    Toast.makeText(activity, "Любимое имя кота:  ${catNames[item]}",
                        Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("OK"
                ) { dialog, id ->
                    // User clicked OK, so save the selectedItems results somewhere
                    // or return them to the component that opened the dialog
                }
                .setNegativeButton("Отмена") {
                        dialog, id ->
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    inline fun base(block: BaseHostActivity.() -> Unit) {
        activity.base(block)
    }

    inline fun <reified T : ViewModel> viewModel(body: T.() -> Unit): T {
        val vm = ViewModelProviders.of(requireActivity(), viewModelFactory)[T::class.java]
        vm.body()
        return vm
    }
}