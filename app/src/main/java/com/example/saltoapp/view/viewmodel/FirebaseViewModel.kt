package com.example.saltoapp.view.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.saltoapp.view.model.Store
import com.example.saltoapp.view.model.User
import com.example.saltoapp.view.repo.Repository

class FirebaseViewModel: ViewModel() {

    val repo = Repository()

    suspend fun createUserAccount(user: User, context: Context){
        return repo.createUserAccount(user, context)
    }


    suspend fun getDoorsStatus(user: User): Store {
        return repo.getDoorsStatus(user)
    }

    suspend fun getUser(user: String): User{
        return repo.getUser(user)
    }

    suspend fun setDoorStatus(store: Store) {
        return repo.setDoorStatus(store)
    }
}