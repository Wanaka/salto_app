package com.example.saltoapp.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.example.saltoapp.R
import com.example.saltoapp.view.model.User
import com.example.saltoapp.view.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_create_employee.*
import kotlinx.android.synthetic.main.activity_create_store.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateEmployeeActivity : AppCompatActivity() {

    private lateinit var viewModel: FirebaseViewModel
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private lateinit var user: String
    private lateinit var userAccess: User
    lateinit var context: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_employee)
        setSupportActionBar(custom_toolbar)
        supportActionBar?.title = getString(R.string.add_new_employee)
        context = this

        val employee: View = findViewById(R.id.createEmployee)
        val employeeInput = employee.findViewById<EditText>(R.id.employee_input)
        val employeeSwitchFrontDoor = employee.findViewById<Switch>(R.id.employee_switch_front_door)
        val employeeSwitchStorageRoom =
            employee.findViewById<Switch>(R.id.employee_switch_storage_room)

        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel::class.java)

        init()


        createEmployeeBtn.setOnClickListener {
            createEmployee(
                User(
                    employeeInput.text.toString(),
                    employeeSwitchFrontDoor.isChecked,
                    employeeSwitchStorageRoom.isChecked,
                    userAccess.store,
                    false
                )
            )
        }
    }

    private fun init() {
        user = currentUser?.email.toString().substringBefore("@")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                userAccess = viewModel.getUser(user)
                Log.d(",,,", "USERACCESS: ${userAccess.name}")

            } catch (e: Error) {
                Log.d(",,,", "Error: $e")
            }
        }
    }

    private fun createEmployee(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                viewModel.createUserAccount(user, userAccess.name, context)
                Log.d(",,,", "Created new user!!!")
                finish()
            } catch (e: Error) {
                Log.d(",,,", "Error: $e")
            }
        }
    }


}
