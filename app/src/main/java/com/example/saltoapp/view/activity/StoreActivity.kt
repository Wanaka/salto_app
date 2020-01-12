package com.example.saltoapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.saltoapp.R
import kotlinx.android.synthetic.main.custom_toolbar.*

class StoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)
        setSupportActionBar(custom_toolbar)
        supportActionBar?.title = "User Name"
    }
}
