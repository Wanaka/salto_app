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

        db.collection("users").document(user.name).collection(user.name)
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(",,,", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(",,,", "Error adding document", e)
            }
    }

    private fun createStore(user: User){

        val store = Store(user.store, frontDoor = false, storageRoom = false)

        db.collection("store").document(user.store).collection(user.store)
            .add(store)
            .addOnSuccessListener { documentReference ->
                Log.d(",,,", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(",,,", "Error adding document", e)
            }
    }

    suspend fun getUser(user: String): User {
        lateinit var mUser: User

        return try {
            var path = db.collection("users").document(user).collection(user)
            var document = Tasks.await(path.get())

                    if (document.documents != null) {

                        for (i in document.documents) {

                            mUser = User(i["name"].toString(),
                                i["frontDoor"].toString().toBoolean(),
                                i["storageRoom"].toString().toBoolean(),
                                i["store"].toString())
                        }
                    } else {
                        Log.d(",,,", "No such document")
                    }
            mUser
        } finally {

        }
    }

    suspend fun getDoorsStatus(user: User): Store {
        Log.d(",,,", "Check user: ${user.store}")

        lateinit var store: Store

        return try {
            var path = db.collection("store").document(user.store).collection(user.store)
            var document = Tasks.await(path.get())

            if (document.documents != null) {

                for (i in document.documents) {
                    Log.d(",,,", "DocumentSnapshot data: ${i["store"]}")
                    store = Store(i["store"].toString(),
                        i["frontDoor"].toString().toBoolean(),
                        i["storageRoom"].toString().toBoolean())
                }
                }

            store
        } finally {

        }
    }
}