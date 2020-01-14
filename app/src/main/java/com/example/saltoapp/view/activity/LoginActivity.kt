package com.example.saltoapp.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.saltoapp.R
import com.example.saltoapp.view.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: FirebaseViewModel
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this
        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel::class.java)



        btn_login.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch{
                try {
                    viewModel.loginUser(login_text.text.toString(), context)
                } catch (e: Error) {
                    Log.d(",,,", "Error: $e")
                }
            }
        }
    }
}
