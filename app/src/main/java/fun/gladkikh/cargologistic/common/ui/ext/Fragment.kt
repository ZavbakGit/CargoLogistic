package `fun`.gladkikh.cargologistic.common.ui.ext

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.showDialog(show:Boolean,
                               tag:String,
                               dialogInstance:()-> DialogFragment){

    var dialog = this
        .findFragmentByTag(tag) as? DialogFragment

    if (dialog == null) {
        dialog = dialogInstance()
    }

    if (show == true) {
        if (!dialog.isVisible) {
            dialog.show(this, tag)
        }
    } else {
        try {
            dialog.dismiss()
        } catch (e: Exception) {
        }
    }

}