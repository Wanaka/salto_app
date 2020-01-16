package com.example.saltoapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.saltoapp.R
import com.example.saltoapp.view.fragment.AuthFragment
import com.example.saltoapp.view.fragment.DoorsFragment
import com.example.saltoapp.view.fragment.EventListFragment
import com.example.saltoapp.view.model.User
import com.example.saltoapp.view.navigator.NavigatorImpl
import com.example.saltoapp.view.viewmodel.FirebaseViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StoreActivity : AppCompatActivity() {

    private lateinit var viewModel: FirebaseViewModel
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val navigator = NavigatorImpl()
    private lateinit var userAuth: String
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
        setContentView(R.layout.activity_store)
        setSupportActionBar(custom_toolbar)
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel::class.java)
        userAuth = currentUser?.email.toString().substringBefore("@")

        init()
    }

    private fun init() {
        replaceFragment(DoorsFragment())

        CoroutineScope(Dispatchers.IO).launch {
            try {
                user = viewModel.getUser(userAuth)

                withContext(Main) {
                    supportActionBar?.title = user.store
                    when (user.isAdmin){
                        false -> bottomNavigationBar.menu.removeItem(R.id.authorization)
                    }
                }

            } catch (e: Error) {
                Log.d(",,,", "Error: $e")
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                navigator.newEvent(this, StartActivity())
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }
}
