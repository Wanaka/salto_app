package com.example.saltoapp.view.repo

import android.content.Context
import android.util.Log
import com.example.saltoapp.view.activity.StoreActivity
import com.example.saltoapp.view.model.Store
import com.example.saltoapp.view.model.User
import com.example.saltoapp.view.navigator.NavigatorImpl
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FireBaseService {


    private var auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()


    private val navigator = NavigatorImpl()

    suspend fun createUserAccount(user: User, context: Context){
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

    private fun addUserDB(user: User){

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

    private fun createStore(user: User){

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

    suspend fun getUser(user: String): User {
        lateinit var mUser: User

        return try {
            var path = db.collection("users").document(user)
            var document = Tasks.await(path.get())

                    if (document != null) {


                            mUser = User(document["name"].toString(),
                                document["frontDoor"].toString().toBoolean(),
                                document["storageRoom"].toString().toBoolean(),
                                document["store"].toString())

                    } else {
                        Log.d(",,,", "No such document")
                    }
            mUser
        } finally {

        }
    }

    suspend fun getDoorsStatus(user: User): Store {
        lateinit var store: Store

        return try {
            var path = db.collection("store").document(user.store)
            var document = Tasks.await(path.get())

            if (document != null) {

                    Log.d(",,,", "DocumentSnapshot data: ${document["store"]}")
                    store = Store(document["store"].toString(),
                        document["frontDoor"].toString().toBoolean(),
                        document["storageRoom"].toString().toBoolean())

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
}