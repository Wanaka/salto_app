package com.example.saltoapp.view.navigator

import android.app.Activity
import android.content.Context

interface Navigator {
    fun newEvent(context: Context, activity: Activity)
    fun accessDeniedToast(context: Context)

}