package com.android.nikhil.memorygame

import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_game.view.*

class GameDialog : DialogFragment() {

    companion object {
        fun newInstance(message: String): GameDialog {
            val frag = GameDialog()
            val args = Bundle()
            args.putString("message", message)
            frag.arguments = args
            return frag
        }
    }

    private var dismissListener: DialogInterface.OnDismissListener? = null

    fun setOnDismissListener(listener: DialogInterface.OnDismissListener?): GameDialog {
        dismissListener = listener
        return this
    }

    override fun onDismiss(dialog: DialogInterface?) {
        dismissListener?.onDismiss(dialog)
        super.onDismiss(dialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_game, null)
        val message = arguments.getString("message")
        message?.let { view.message.text = it }
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}