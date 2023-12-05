package com.zalomsky.sendto.domain.repository

import android.view.View

interface AccountRepository {

    val currentUserId: String
    val hasUser: Boolean

    suspend fun registration(name: String, email: String, phone: String, password: String, view: View)
    suspend fun authenticate(email: String, password: String, view: View)
    suspend fun deleteAccount()

    //todo: Fix update
    suspend fun updateAccount(name: String, phone: String, email: String, password: String)
    suspend fun signOut()
    suspend fun showHeaderData(name: String, email: String)
    suspend fun getUsersData(view: View)
}