package com.example.saltoapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders

import com.example.saltoapp.R
import com.example.saltoapp.view.model.DoorInteraction
import com.example.saltoapp.view.model.Store
import com.example.saltoapp.view.model.User
import com.example.saltoapp.view.navigator.NavigatorImpl
import com.example.saltoapp.view.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_doors.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DoorsFragment : Fragment() {

    private val navigator = NavigatorImpl()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private lateinit var viewModel: FirebaseViewModel
    lateinit var userAuth: User
    lateinit var doors: Store
    private lateinit var user: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel::class.java)

        user = currentUser?.email.toString().substringBefore("@")

        CoroutineScope(IO).launch {
            try {
                var userAccess = viewModel.getUser(user)
                var store = viewModel.getDoorsStatus(userAccess)

                withContext(Main) {
                    updateUI(store)

                    userAuth = userAccess
                    doors = store
                }

            } catch (e: Error) {
                Log.d(",,,", "Error: $e")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doorsButtonClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_doors, container, false)
    }

    private fun updateUI(store: Store) {
        when (store.frontDoor) {
            true -> openDoor(front_door_view, front_door_status, btn_frontDoor)
            else -> lockedDoor(front_door_view, front_door_status, btn_frontDoor)
        }

        when (store.storageRoom) {
            true -> openDoor(storage_room_view, storage_room_status, btn_storageRoom)
            else -> lockedDoor(storage_room_view, storage_room_status, btn_storageRoom)
        }
    }

    private fun doorsButtonClick() {
        btn_frontDoor.setOnClickListener {
            changeDoorStatus(userAuth.frontDoor, "Front Door")
        }

        btn_storageRoom.setOnClickListener {
            changeDoorStatus(userAuth.storageRoom, "Storage Room")
        }
    }

    private fun changeDoorStatus(userCheck: Boolean, door: String) {
        if (userCheck) {
            CoroutineScope(IO).launch {
                try {
                    when (door) {
                        "Front Door" -> {
                            viewModel.setDoorStatus(checkDoor(door))
                            updateEventList("Front Door", true)
                            updateUI(
                                doors.frontDoor,
                                front_door_view,
                                front_door_status,
                                btn_frontDoor
                            )
                        }
                        "Storage Room" -> {
                            viewModel.setDoorStatus(checkDoor(door))
                            updateEventList("Storage Room", true)
                            updateUI(
                                doors.storageRoom,
                                storage_room_view,
                                storage_room_status,
                                btn_storageRoom
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            CoroutineScope(IO).launch {
                try {
                    when (door) {
                        "Front Door" -> {
                            updateEventList("Front Door", false)
//                        viewModel.sendToEventList(DoorInteraction(user, doors.store, false,"Front Door"))
                        }
                        "Storage Room" -> {
                            updateEventList("Storage Room", false)

//                        viewModel.sendToEventList(DoorInteraction(user, doors.store, false, "Storage Room"))
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            navigator.accessDeniedToast(context!!)
        }
    }

    private fun checkDoor(door: String): Store {
        if (door == "Front Door") {
            doors.frontDoor = !doors.frontDoor
        } else if (door == "Storage Room") {
            doors.storageRoom = !doors.storageRoom
        }
        return doors
    }

    private suspend fun updateEventList(doorName: String, access: Boolean) {
        viewModel.sendToEventList(DoorInteraction(user, doors.store, access, doorName))
    }

    private suspend fun updateUI(door: Boolean, view: View, status: TextView, button: Button) {
        withContext(Main) {
            when (door) {
                false -> lockedDoor(view, status, button)
                else -> openDoor(view, status, button)
            }
        }
    }

    private fun openDoor(view: View, status: TextView, button: Button) {
        view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorGreen))
        status.text = getText(R.string.is_open)
        button.text = getText(R.string.lock_door)
    }

    private fun lockedDoor(view: View, status: TextView, button: Button) {
        view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorRed))
        status.text = getText(R.string.is_locked)
        button.text = getText(R.string.open_door)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }
}
