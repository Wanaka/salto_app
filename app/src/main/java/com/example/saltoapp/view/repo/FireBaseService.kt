package com.example.saltoapp.view.repo

import android.content.Context
import android.util.Log
import com.example.saltoapp.view.activity.StoreActivity
import com.example.saltoapp.view.model.DoorInteraction
import com.example.saltoapp.view.model.Store
import com.example.saltoapp.view.model.User
import com.example.saltoapp.view.navigator.NavigatorImpl
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class FireBaseService {

    private var auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val navigator = NavigatorImpl()

// Users

    suspend fun createAdminAccount(user: User, context: Context) {
        auth.createUserWithEmailAndPassword("${user.name}@mail.com", "123456")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    CoroutineScope(IO).launch {
                        addUserDB(user)
                        createStore(user)
                    }
                    navigator.newEvent(context, StoreActivity())
                } else {
                    Log.w(",,,", "Error adding user")
                }
            }
    }

    suspend fun createUserAccount(user: User, admin: String, context: Context) {
        auth.createUserWithEmailAndPassword("${user.name}@mail.com", "123456")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    CoroutineScope(IO).launch {
                        addUserDB(user)
                        loginAdmin(admin, context)
                    }
                } else {
                    Log.w(",,,", "Error adding user")
                }
            }
    }

    private suspend fun loginAdmin(name: String, context: Context) {
        auth.signInWithEmailAndPassword("${name}@mail.com", "123456")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                } else {
                    Log.w(",,,", "signInWithEmail:failure", task.exception)
                }
            }
    }

    suspend fun loginUser(name: String, context: Context) {
        auth.signInWithEmailAndPassword("${name}@mail.com", "123456")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigator.newEvent(context, StoreActivity())
                } else {
                    Log.w(",,,", "signInWithEmail:failure", task.exception)
                }
            }
    }


    // DB

    private suspend fun createStore(user: User) {
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

    suspend fun addUserDB(user: User) {
        db.collection("users").document(user.name)
            .set(user)
            .addOnSuccessListener { documentReference ->
                Log.d(",,,", "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w(",,,", "Error adding document", e)
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
                    document["store"].toString(),
                    document["admin"].toString().toBoolean()
                )
            } else {
                Log.d(",,,", "No such document")
            }

            user
        } finally {

        }
    }

    suspend fun getAllStoreUsers(store: String): ArrayList<User> {
        var storeUsers: ArrayList<User> = ArrayList()

        return try {
            var path = db.collection("users")
            var document = Tasks.await(path.get())

            if (document.documents != null) {
                for (i in document.documents) {
                    if (i["store"] == store) {
                        storeUsers.add(
                            User(
                                i.get("name").toString(),
                                i.get("frontDoor").toString().toBoolean(),
                                i.get("storageRoom").toString().toBoolean(),
                                i.get("store").toString(),
                                i.get("admin").toString().toBoolean()
                            )
                        )
                    }

                }
            }

            storeUsers
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