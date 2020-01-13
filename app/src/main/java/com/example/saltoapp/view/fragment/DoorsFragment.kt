package com.example.saltoapp.view.fragment

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.saltoapp.R
import com.example.saltoapp.view.model.Store
import com.example.saltoapp.view.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_doors.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DoorsFragment : Fragment() {

    private lateinit var viewModel: FirebaseViewModel

    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel::class.java)

        val user = user?.email.toString().substringBefore("@")

        CoroutineScope(Dispatchers.IO).launch{
            try {
                var userAccess = viewModel.getUser(user)

               var store = viewModel.getDoorsStatus(userAccess)

                withContext(Main){
                    frontDoor(store)
                }
            } catch (e: Error) {
                Log.d(",,,", "Error: $e")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_doors, container, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }


    fun frontDoor(store: Store){
        front_door_view.setBackgroundColor(Color.parseColor("#ffffff"))

    }
}
