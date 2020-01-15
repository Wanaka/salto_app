package com.example.saltoapp.view.navigator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.saltoapp.R

class NavigatorImpl : Navigator {

    override fun newEvent(context: Context,  activity: Activity) {
        val intent = Intent(context, activity::class.java)
        startActivity(context, intent, null)
    }

    override fun accessDeniedToast(context: Context) {
        val toast: Toast = Toast.makeText(context, R.string.access_denied, Toast.LENGTH_LONG)
        val toastView = toast.view

        val toastMessage = toastView.findViewById<View>(android.R.id.message) as TextView
        toastMessage.textSize = 25f
        toastMessage.setTextColor(ContextCompat.getColor(context!!, R.color.colorWhite))
        toastMessage.gravity = Gravity.CENTER
        toastMessage.compoundDrawablePadding = 16
        toastView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorRed))
        toast.show()
    }
}