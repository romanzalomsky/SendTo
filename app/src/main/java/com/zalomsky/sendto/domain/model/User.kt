package com.zalomsky.sendto.domain.model

import com.google.firebase.database.Exclude

data class User(
    val id: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val password: String? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "id" to id,
            "name" to name,
            "phone" to phone,
            "email" to email,
            "password" to password
        )
    }
}

