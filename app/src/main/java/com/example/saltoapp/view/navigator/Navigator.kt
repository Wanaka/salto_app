package com.example.saltoapp.view.navigator

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.saltoapp.view.model.User

interface Navigator {
    fun newEvent(context: Context, activity: Activity)

}