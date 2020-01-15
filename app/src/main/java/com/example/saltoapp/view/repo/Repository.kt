package com.example.saltoapp.view.repo

import android.content.Context
import android.util.Log
import com.example.saltoapp.view.model.DoorInteraction
import com.example.saltoapp.view.model.Store
import com.example.saltoapp.view.model.User


class Repository {

    val firebase = FireBaseService()

    suspend fun createUserAccount(user: User, context: Context){
        return firebase.createUserAccount(user, context)
    }

    suspend fun loginUser(name: String, context: Context){
        return firebase.loginUser(name, context)
    }

    suspend fun getDoorsStatus(user: User): Store {
        return firebase.getDoorsStatus(user)
    }

    suspend fun getUser(user: String): User {
        return firebase.getUser(user)
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