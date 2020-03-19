package `fun`.gladkikh.dialogviewmodel.ui

import `fun`.gladkikh.dialogviewmodel.R
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.dialod_test1.*

class TestDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "TestDialogFragment"
    }

    var dialogContract: DialogContract? = null
    private val layoutId = R.layout.dialod_test1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialogContract?.commandDialog()?.observe(viewLifecycleOwner, Observer {
            when (it) {
                is CloseDialog -> {
                    dismiss()
                }
            }
        })

        dialogContract?.getStateLiveData()?.observe(viewLifecycleOwner, Observer {
            tvMessage.text = it.title
        })

        btOk.setOnClickListener {
            dialogContract?.setEventDialog(Result(10))
        }
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        dialogContract?.setEventDialog(Cancel())
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        dialogContract?.setEventDialog(Closed())
    }

    interface DialogContract {
        fun commandDialog(): LiveData<CommandToDialog>
        fun getStateLiveData(): LiveData<State>
        fun setEventDialog(eventDialog: EventDialog)
    }

    class State(val title:String)

}



sealed class EventDialog
class Result(val i: Int) : EventDialog()
class Cancel() : EventDialog()
class Closed() : EventDialog()


sealed class CommandToDialog
class CloseDialog : CommandToDialog()