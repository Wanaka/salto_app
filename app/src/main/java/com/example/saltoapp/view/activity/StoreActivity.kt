package com.example.saltoapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.saltoapp.R
import com.example.saltoapp.view.navigator.NavigatorImpl
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.custom_toolbar.*

class StoreActivity : AppCompatActivity() {

    private val navigator = NavigatorImpl()
    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)
        setSupportActionBar(custom_toolbar)
        supportActionBar?.title = "User Name"

        if(user == null){
            navigator.newEvent(this, StartActivity())
        }
    }

    public override fun onStart() {
        super.onStart()
    }
}
