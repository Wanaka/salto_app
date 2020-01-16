package com.example.saltoapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.saltoapp.R
import com.example.saltoapp.view.activity.CreateEmployeeActivity
import com.example.saltoapp.view.model.User
import com.example.saltoapp.view.navigator.NavigatorImpl
import com.example.saltoapp.view.recyclerView.UserListAdapter
import com.example.saltoapp.view.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_authorization.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AuthFragment : Fragment(), UserListAdapter.OnItemClickListener {

    private lateinit var viewModel: FirebaseViewModel
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val navigator = NavigatorImpl()
    private lateinit var userAuth: String
    lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel::class.java)
        userAuth = currentUser?.email.toString().substringBefore("@")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        fabBtn()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_authorization, container, false)
    }

    private fun init(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                user = viewModel.getUser(userAuth)
                var list = viewModel.getAllStoreUsers(user.store)

                withContext(Dispatchers.Main) {
                    employee_recyclerView.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = UserListAdapter(list, context, this@AuthFragment)
                    }
                }
            } catch (e: Error) {
                Log.d(",,,", "Error: $e")
            }
        }
    }

    private fun fabBtn(){
        addEmployee.setOnClickListener { navigator.newEvent(context!!, CreateEmployeeActivity()) }
    }

    override fun onItemClick(doorName: String, mUser: String, frontDoor: Boolean, storageRoom: Boolean, isAdmin: Boolean) {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                when(doorName){
                    "Front Door" -> viewModel.addUserDB(User(mUser, frontDoor, storageRoom, user.store, isAdmin))
                    "Storage Room" -> viewModel.addUserDB(User(mUser, frontDoor, storageRoom, user.store, isAdmin))
                }
            } catch (e: Error) {
                Log.d(",,,", "Error: $e")
            }
        }
    }
}
