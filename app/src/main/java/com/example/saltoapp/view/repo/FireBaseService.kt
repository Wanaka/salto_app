package com.example.saltoapp.view.repo

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.saltoapp.view.activity.StoreActivity
import com.example.saltoapp.view.model.DoorInteraction
import com.example.saltoapp.view.model.Store
import com.example.saltoapp.view.model.User
import com.example.saltoapp.view.navigator.NavigatorImpl
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import kotlin.collections.ArrayList

class FireBaseService {


    private var auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val navigator = NavigatorImpl()


    suspend fun createUserAccount(user: User, context: Context) {
        auth.createUserWithEmailAndPassword("${user.name}@mail.com", "123456")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    addUserDB(user)
                    createStore(user)
                    navigator.newEvent(context, StoreActivity())
                } else {

                }
            }
    }

    private fun addUserDB(user: User) {
        val user = User(user.name, user.frontDoor, user.storageRoom, user.store)

        db.collection("users").document(user.name)
            .set(user)
            .addOnSuccessListener { documentReference ->
                Log.d(",,,", "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w(",,,", "Error adding document", e)
            }
    }

    private fun createStore(user: User) {
        val store = Store(user.store, frontDoor = false, storageRoom = false)

        db.collection("store").document(user.store)
            .set(store)
            .addOnSuccessListener { documentReference ->
                Log.d(",,,", "DocumentSnapshot added with ID:")
            }
            .addOnFailureListener { e ->
                Log.w(",,,", "Error adding document", e)
            }
    }

    suspend fun loginUser(name: String, context: Context) {
        auth.signInWithEmailAndPassword("${name}@mail.com", "123456")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigator.newEvent(context, StoreActivity())
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(",,,", "signInWithEmail:failure", task.exception)
                }
            }
    }

    suspend fun getUser(name: String): User {
        lateinit var user: User

        return try {
            var path = db.collection("users").document(name)
            var document = Tasks.await(path.get())

            if (document != null) {
                user = User(
                    document["name"].toString(),
                    document["frontDoor"].toString().toBoolean(),
                    document["storageRoom"].toString().toBoolean(),
                    document["store"].toString()
                )
            } else {
                Log.d(",,,", "No such document")
            }
            user
        } finally {

        }
    }

    suspend fun getDoorsStatus(user: User): Store {
        lateinit var store: Store

        return try {
            var path = db.collection("store").document(user.store)
            var document = Tasks.await(path.get())

            if (document != null) {
                store = Store(
                    document["store"].toString(),
                    document["frontDoor"].toString().toBoolean(),
                    document["storageRoom"].toString().toBoolean()
                )
            }

            store
        } finally {

        }
    }

    suspend fun setDoorStatus(store: Store) {
        db.collection("store").document(store.store)
            .set(store)
            .addOnSuccessListener { documentReference ->
                Log.d(",,,", "DocumentSnapshot added with ID: ")
            }
            .addOnFailureListener { e ->
                Log.w(",,,", "Error adding document", e)
            }
    }

    suspend fun sendToEventList(doorInteraction: DoorInteraction) {
        db.collection("store").document(doorInteraction.store).collection("List")
            .document(Date().time.toString())
            .set(doorInteraction)
            .addOnSuccessListener { Log.d(",,,", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(",,,", "Error writing document", e) }
    }

    suspend fun getEventList(store: String): ArrayList<DoorInteraction> {

        var eventList: ArrayList<DoorInteraction> = ArrayList()

        return try {
            var path = db.collection("store").document(store).collection("List")
            var document = Tasks.await(path.get())

            if (document.documents != null) {
                for (i in document.documents) {

                    eventList.add(
                        DoorInteraction(
                            i.get("user").toString(),
                            i.get("store").toString(),
                            i.get("access").toString().toBoolean(),
                            i.get("door").toString()
                        )
                    )
                }
            }

            eventList
        } finally {

        }
    }
}