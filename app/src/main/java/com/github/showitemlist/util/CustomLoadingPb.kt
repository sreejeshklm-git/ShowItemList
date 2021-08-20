package com.github.showitemlist.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.github.showitemlist.R

class CustomLoadingPb (context: Context) {
    private var context: Context? = context
    private var dialog: Dialog? = null

    fun show() {
        try {
            dialog = Dialog(context!!)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setCancelable(false)
            dialog!!.setContentView(R.layout.custom_loading_pb)
            val width = (context!!.resources.displayMetrics.widthPixels * 0.70).toInt()
            val height = WindowManager.LayoutParams.WRAP_CONTENT
            if (dialog!!.window != null) {
                dialog!!.window!!.setLayout(width, height)
                dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            if (dialog!! != null) {
                dialog!!.show()
            }
        } catch (e: Exception) {
            Log.e("Error-CustomLoadingPb-", e.toString())
        }
    }

    fun isShowing(): Boolean {
        if (dialog != null) {
            return dialog!!.isShowing
        }
        return false

    }

    fun dismiss() {
        if (dialog != null) dialog!!.dismiss()
    }
}