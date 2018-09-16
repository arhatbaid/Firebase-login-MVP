package com.android.studentconnect.ui.dialog

import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import com.android.studentconnect.R

class DialogSingleChoice(var context: Context, var dialogCallback: Callback?, @StringRes var title: Int) {

    init {
        this.context = context
        this.title = title
        this.dialogCallback = dialogCallback
    }

    fun show(items: ArrayList<*>) {
        var arrList = items.map { it.toString() }.toTypedArray()
        AlertDialog.Builder(context, R.style.AppTheme_Dialog)
                .setTitle(title)
                .setSingleChoiceItems(arrList, -1, null)
                .setPositiveButton("Ok") { dialog, _ ->
                    val selectedPosition = (dialog as AlertDialog).listView.checkedItemPosition
                    if (selectedPosition >= 0 && selectedPosition < items.size){
                        dialogCallback?.onDialogItemSelected(items, selectedPosition)
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()

//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.resources.getColor(R.color.colorAccent));

    }

    /* fun show(items: Int, tag: Int) {
         AlertDialog.Builder(context)
                 .setTitle(title)
                 .setSingleChoiceItems(items, -1, null)
                 .setPositiveButton("OK") { dialog, _ ->

                     val selectedPosition = (dialog as AlertDialog).listView.checkedItemPosition
                     if (selectedPosition >= 0 && selectedPosition < items.size)
                         dialogCallback?.onDialogItemSelected(items[selectedPosition], context.getString(tag))
                     dialog.dismiss()

                 }
                 .show()

 //        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.resources.getColor(R.color.colorAccent));

     }*/

    interface Callback {
        fun onDialogItemSelected(items: ArrayList<*>, position: Int)
    }

}