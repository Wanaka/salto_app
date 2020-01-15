package com.example.saltoapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.saltoapp.R
import com.example.saltoapp.view.fragment.AuthFragment
import com.example.saltoapp.view.fragment.DoorsFragment
import com.example.saltoapp.view.fragment.EventListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class StoreActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
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
        supportActionBar?.title = "User Name"
        init()
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun init(){
        replaceFragment(DoorsFragment())
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
