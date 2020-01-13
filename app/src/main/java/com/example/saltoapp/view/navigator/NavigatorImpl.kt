package com.example.saltoapp.view.navigator

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

class NavigatorImpl : Navigator {

    override fun newEvent(context: Context, activity: Activity) {
        val intent = Intent(context, activity::class.java)
//        intent.putExtra("key", value)
        startActivity(context, intent, null)
    }
}