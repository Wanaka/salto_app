package com.example.saltoapp.view.repo

import android.content.Context
import com.example.saltoapp.view.model.DoorInteraction
import com.example.saltoapp.view.model.Store
import com.example.saltoapp.view.model.User


class Repository {

    val firebase = FireBaseService()

    suspend fun createAdminAccount(user: User, context: Context) {
        return firebase.createAdminAccount(user, context)
    }

    suspend fun createUserAccount(user: User, admin: String, context: Context) {
        return firebase.createUserAccount(user, admin, context)
    }

    suspend fun addUserDB(user: User) {
        return firebase.addUserDB(user)
    }

    suspend fun loginUser(name: String, context: Context) {
        return firebase.loginUser(name, context)
    }

    suspend fun getDoorsStatus(user: User): Store {
        return firebase.getDoorsStatus(user)
    }

    suspend fun getUser(user: String): User {
        return firebase.getUser(user)
    }

    suspend fun getAllStoreUsers(user: String): ArrayList<User> {
        return firebase.getAllStoreUsers(user)
    }

    suspend fun setDoorStatus(store: Store) {
        return firebase.setDoorStatus(store)
    }

    suspend fun sendToEventList(doorInteraction: DoorInteraction) {
        return firebase.sendToEventList(doorInteraction)
    }

    suspend fun getEventList(store: String): ArrayList<DoorInteraction> {
        return firebase.getEventList(store)
    }
}