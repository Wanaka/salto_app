package com.example.saltoapp.view.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.saltoapp.R
import com.example.saltoapp.view.model.User
import com.example.saltoapp.view.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.activity_create_store.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class CreateStoreActivity : AppCompatActivity() {

    private var frontDoor: Boolean = false
    private var storageRoom: Boolean = false

    lateinit var context: Context

    private lateinit var viewModel: FirebaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_store)
        setSupportActionBar(custom_toolbar)
        supportActionBar?.title = getString(R.string.create_new_store)
        context = this

        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel::class.java)

        val employee1: View = findViewById(R.id.employee1_auth)
        val employee1_title = employee1.findViewById<TextView>(R.id.employee_title)
        val employee1_input = employee1.findViewById<EditText>(R.id.employee_input)
        val employee1_switch_front_door = employee1.findViewById<Switch>(R.id.employee_switch_front_door)
        val employee1_switch_storage_room = employee1.findViewById<Switch>(R.id.employee_switch_storage_room)

        employee1_title.text = "You (Admin)"

        employee1_switch_front_door?.setOnCheckedChangeListener { _, isChecked -> frontDoor = isChecked }
        employee1_switch_storage_room?.setOnCheckedChangeListener { _, isChecked -> storageRoom = isChecked }

        button_create.setOnClickListener {
            createNewAccount(User(employee1_input.text.toString(), frontDoor, storageRoom, store_name.text.toString(), true))
        }

    }

    public override fun onStart() {
        super.onStart()
    }

    private fun createNewAccount(user: User) = runBlocking{
        CoroutineScope(IO).launch{
            try {
                viewModel.createAdminAccount(user, context)
            } catch (e: Error) {
                Log.d(",,,", "Error: $e")
            }
        }
    }
}
