package com.example.saltoapp.view.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.saltoapp.R
import com.example.saltoapp.view.recyclerView.EventListAdapter
import com.example.saltoapp.view.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_event_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EventListFragment : Fragment() {

    private lateinit var viewModel: FirebaseViewModel
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private lateinit var user: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel::class.java)
        user = currentUser?.email.toString().substringBefore("@")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    private fun init(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var user = viewModel.getUser(user)
                var list = viewModel.getEventList(user.store)

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
}
