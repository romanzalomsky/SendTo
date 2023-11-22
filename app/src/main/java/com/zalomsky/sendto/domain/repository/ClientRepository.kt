package com.zalomsky.sendto.domain.repository

import android.view.View
import com.zalomsky.sendto.domain.model.Client

interface ClientRepository {

    suspend fun add(id: String, email: String, phone: String, view: View, addressBookId: String)
}