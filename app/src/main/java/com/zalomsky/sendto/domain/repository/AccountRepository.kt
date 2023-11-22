package com.zalomsky.sendto.domain.repository

import android.view.View
import com.zalomsky.sendto.domain.model.Role
import com.zalomsky.sendto.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    val currentUserId: String
    val hasUser: Boolean

    //todo: Fix registration
    suspend fun registration(name: String, email: String, phone: String, password: String)
    suspend fun authenticate(email: String, password: String, view: View)
    suspend fun deleteAccount()
    suspend fun updateAccount(name: String, email: String, phone: String, password: String)
    suspend fun showAccount(name: String, email: String)
    suspend fun signOut()
}