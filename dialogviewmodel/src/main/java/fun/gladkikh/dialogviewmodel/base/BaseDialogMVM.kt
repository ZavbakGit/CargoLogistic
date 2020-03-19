package `fun`.gladkikh.dialogviewmodel.base





import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


abstract class BaseDialogMVM<STATE, RESULT> : DialogFragment() {
//    var dialogContract: DialogContract<STATE, RESULT>? = null
//
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        dialogContract?.commandDialog()?.observe(viewLifecycleOwner, Observer {
//            when (it) {
//                is DialogContract.CommandToDialog.CloseDialog -> {
//                    dismiss()
//                }
//            }
//        })
//
//        dialogContract?.getStateLiveData()?.observe(viewLifecycleOwner, Observer {
//            handleState(it)
//        })
//    }
//
//
//    abstract fun handleState(state: STATE)
//    override fun onCancel(dialog: DialogInterface?) {
//        super.onCancel(dialog)
//        dialogContract?.setEventDialog(Cancel)
//    }
//
//    override fun onDismiss(dialog: DialogInterface?) {
//        super.onDismiss(dialog)
//        dialogContract?.setEventDialog(Closed)
//    }
//
//    fun setResult(result: RESULT) {
//        dialogContract?.setResult(result)
//    }
//
//
////    interface DialogContract<STATE, RESULT> {
////        fun commandDialog(): LiveData<CommandToDialog>
////        fun getStateLiveData(): LiveData<STATE>
////        fun setEventDialog(eventDialog: EventDialog)
////        fun setResult(result: RESULT)
////
////        sealed class EventDialog {
////            object Cancel : EventDialog()
////            object Closed : EventDialog()
////        }
////
////        sealed class CommandToDialog {
////            object CloseDialog : CommandToDialog()
////        }
////    }
//
//    abstract class DialogContract<STATE, RESULT> {
//        protected val commandData = SinL
//
//        fun commandDialog(): LiveData<CommandToDialog>{
//
//        }
//
//        fun getStateLiveData(): LiveData<STATE>
//        fun setEventDialog(eventDialog: EventDialog)
//        fun setResult(result: RESULT)
//
//        sealed class EventDialog {
//            object Cancel : EventDialog()
//            object Closed : EventDialog()
//        }
//
//        sealed class CommandToDialog {
//            object CloseDialog : CommandToDialog()
//        }
//    }


}