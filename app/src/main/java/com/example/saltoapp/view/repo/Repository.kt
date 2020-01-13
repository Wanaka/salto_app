package com.example.saltoapp.view.repo

import android.content.Context
import android.util.Log
import com.example.saltoapp.view.model.Store
import com.example.saltoapp.view.model.User


class Repository {

    val firebase = FireBaseService()

    suspend fun createUserAccount(user: User, context: Context){
        return firebase.createUserAccount(user, context)
    }

    suspend fun getDoorsStatus(user: User): Store {
        return firebase.getDoorsStatus(user)
    }

    suspend fun getUser(user: String): User {
        return firebase.getUser(user)
    }
}