package com.example.saltoapp.view.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.saltoapp.R
import com.example.saltoapp.view.model.DoorInteraction
import com.example.saltoapp.view.recyclerView.EventListAdapter
import com.example.saltoapp.view.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.event_list_item.view.*
import kotlinx.android.synthetic.main.fragment_event_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EventListFragment : Fragment() {

    private lateinit var viewModel: FirebaseViewModel
    private lateinit var user: String
    private val currentUser = FirebaseAuth.getInstance().currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel::class.java)
        user = currentUser?.email.toString().substringBefore("@")


        CoroutineScope(Dispatchers.IO).launch {
            try {
                var user = viewModel.getUser(user)
                var list = viewModel.getEventList(user.store)

                Log.d(",,,", "LIST: $list")
                withContext(Dispatchers.Main) {
                    event_list.apply {
                        layoutManager = LinearLayoutManager(context)

                        adapter = EventListAdapter(list, context)
                    }
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
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
