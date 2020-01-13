package com.example.saltoapp.view.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.saltoapp.R
import com.example.saltoapp.view.navigator.NavigatorImpl
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    val navigator = NavigatorImpl()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        button_create.setOnClickListener {
            goTo(CreateStoreActivity())
        }

        button_login.setOnClickListener {
            goTo(LoginActivity())
        }
    }

    fun goTo(activity: Activity){
        navigator.newEvent(this, activity)
    }

}