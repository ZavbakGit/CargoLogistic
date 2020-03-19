package `fun`.gladkikh.dialogviewmodel.ui

import `fun`.gladkikh.dialogviewmodel.R
import `fun`.gladkikh.dialogviewmodel.base.BaseDialogMVM
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.dialod_test1.*


class TestDialog2 : BaseDialogMVM<String, String>() {

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//            builder.setTitle("Выберите принтер")
//                .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
//                    setResult("Ok")
//                })
//                .setNegativeButton("Cancel") { _, _ ->
//                    setResult("Cansel")
//                }
//
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//
//    }

//    private val layoutId = R.layout.dialod_test1
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(layoutId, container, false)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//        dialogContract?.getStateLiveData()?.observe(viewLifecycleOwner, Observer {
//            tvMessage.text = it
//        })
//
//        btOk.setOnClickListener {
//            dialogContract?.setResult("Result")
//        }
//    }
//
//    override fun handleState(state: String) {
//        dialog.setTitle(state)
//    }

}