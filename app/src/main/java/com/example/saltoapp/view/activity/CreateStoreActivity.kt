package com.example.saltoapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.example.saltoapp.R

class CreateStoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_store)


        val employee1: View = findViewById(R.id.employee1_auth)
        val employee1_title = employee1.findViewById<TextView>(R.id.employee_title)
        val employee1_input = employee1.findViewById<EditText>(R.id.employee_input)
        val employee1_switch_front_door = employee1.findViewById<Switch>(R.id.employee_switch_front_door)
        val employee1_switch_storage_room = employee1.findViewById<Switch>(R.id.employee_switch_storage_room)

        employee1_title.text = "You (Admin)"

    }
}
