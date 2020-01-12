package com.example.saltoapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.saltoapp.R
import kotlinx.android.synthetic.main.activity_create_employee.*
import kotlinx.android.synthetic.main.activity_create_store.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class CreateEmployeeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_employee)
        setSupportActionBar(custom_toolbar)
        supportActionBar?.title = getString(R.string.add_new_employee)
    }
}
