package com.zalomsky.sendto.domain.repository

import com.zalomsky.sendto.domain.model.Role
import com.zalomsky.sendto.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    val currentUserId: String
    val hasUser: Boolean

/*    suspend fun registration(name: String, phone: String, email: String, password: String)*/
    suspend fun authenticate(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun updateAccount(name: String, email: String, phone: String, password: String)
    suspend fun signOut()
}