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

    private lateinit var viewModel: FirebaseViewModel
    lateinit var context: Context
    private var frontDoor: Boolean = false
    private var storageRoom: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_store)
        setSupportActionBar(custom_toolbar)
        supportActionBar?.title = getString(R.string.create_new_store)
        context = this

        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel::class.java)

        val admin: View = findViewById(R.id.employee1_auth)
        val adminTitle = admin.findViewById<TextView>(R.id.employee_title)
        val adminInput = admin.findViewById<EditText>(R.id.employee_input)
        val adminSwitchFrontDoor = admin.findViewById<Switch>(R.id.employee_switch_front_door)
        val adminSwitchStorageRoom = admin.findViewById<Switch>(R.id.employee_switch_storage_room)

        adminTitle.text = getString(R.string.admin)

        adminSwitchFrontDoor?.setOnCheckedChangeListener { _, isChecked -> frontDoor = isChecked }
        adminSwitchStorageRoom?.setOnCheckedChangeListener { _, isChecked -> storageRoom = isChecked }

        button_create.setOnClickListener {
            createNewAccount(User(adminInput.text.toString(), frontDoor, storageRoom, store_name.text.toString(), true))
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
