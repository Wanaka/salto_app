package com.example.saltoapp.view.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.saltoapp.view.model.DoorInteraction
import com.example.saltoapp.view.model.Store
import com.example.saltoapp.view.model.User
import com.example.saltoapp.view.repo.Repository

class FirebaseViewModel : ViewModel() {

    private val repo = Repository()

    suspend fun createAdminAccount(user: User, context: Context) {
        return repo.createAdminAccount(user, context)
    }

    suspend fun createUserAccount(user: User, admin: String, context: Context) {
        return repo.createUserAccount(user, admin, context)
    }

    suspend fun addUserDB(user: User) {
        return repo.addUserDB(user)
    }

    suspend fun loginUser(name: String, context: Context) {
        return repo.loginUser(name, context)
    }

    suspend fun getDoorsStatus(user: User): Store {
        return repo.getDoorsStatus(user)
    }

    suspend fun getUser(user: String): User {
        return repo.getUser(user)
    }

    suspend fun getAllStoreUsers(user: String): ArrayList<User> {
        return repo.getAllStoreUsers(user)
    }

    suspend fun setDoorStatus(store: Store) {
        return repo.setDoorStatus(store)
    }

    suspend fun sendToEventList(doorInteraction: DoorInteraction) {
        return repo.sendToEventList(doorInteraction)
    }

    suspend fun getEventList(store: String): ArrayList<DoorInteraction> {
        return repo.getEventList(store)
    }

}