package com.example.saltoapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.saltoapp.view.di.AppComponent
import com.google.firebase.auth.FirebaseAuth

open class BaseActivity: AppCompatActivity() {

    lateinit var dagger: AppComponent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        DaggerAppComponent.create().inject(this)

    }
}