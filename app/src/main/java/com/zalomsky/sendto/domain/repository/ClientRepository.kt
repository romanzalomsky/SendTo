package com.zalomsky.sendto.domain.repository

import android.view.View

interface ClientRepository {

    suspend fun add(id: String, email: String, phone: String, addressBookId: String, view: View)
}