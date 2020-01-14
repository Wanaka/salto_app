package com.example.saltoapp.view.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.saltoapp.R
import com.example.saltoapp.view.navigator.NavigatorImpl
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    val navigator = NavigatorImpl()
    val user = FirebaseAuth.getInstance().currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        if(user != null){
            navigator.newEvent(this, StoreActivity())
        }

        button_create.setOnClickListener {
            goTo(CreateStoreActivity())
        }

        button_login.setOnClickListener {
            goTo(LoginActivity())
        }
    }

    private fun goTo(activity: Activity){
        navigator.newEvent(this, activity)
    }

}