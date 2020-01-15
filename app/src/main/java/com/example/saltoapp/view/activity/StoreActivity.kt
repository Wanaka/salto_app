package com.example.saltoapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.saltoapp.R
import com.example.saltoapp.view.fragment.AuthFragment
import com.example.saltoapp.view.fragment.DoorsFragment
import com.example.saltoapp.view.fragment.EventListFragment
import com.example.saltoapp.view.model.User
import com.example.saltoapp.view.recyclerView.UserListAdapter
import com.example.saltoapp.view.viewmodel.FirebaseViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.fragment_authorization.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StoreActivity : AppCompatActivity() {

    private lateinit var viewModel: FirebaseViewModel
    private lateinit var userAuth: String
    private val currentUser = FirebaseAuth.getInstance().currentUser
    lateinit var user: User



    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.authorization -> {
                    replaceFragment(AuthFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.doors -> {
                    replaceFragment(DoorsFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.list -> {
                    replaceFragment(EventListFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("onStatus", "onCreate")

        setContentView(R.layout.activity_store)
        setSupportActionBar(custom_toolbar)
        supportActionBar?.title = "User Name"
        init()
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel::class.java)
        userAuth = currentUser?.email.toString().substringBefore("@")


        CoroutineScope(Dispatchers.IO).launch {
            try {
                user = viewModel.getUser(userAuth)

                withContext(Main) {
                    when (user.isAdmin){
                        false -> bottomNavigationBar.menu.removeItem(R.id.authorization)
                    }
                }

            } catch (e: Error) {
                Log.d(",,,", "Error: $e")
            }
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("onStatus", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("onStatus", "onResume")
    }


    override fun onPause() {
        super.onPause()
        Log.d("onStatus", "onPause")
    }


    override fun onStop() {
        super.onStop()
        Log.d("onStatus", "onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onStatus", "onDestroy")

    }
    private fun init() {
        replaceFragment(DoorsFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
