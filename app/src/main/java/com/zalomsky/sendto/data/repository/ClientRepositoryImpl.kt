package com.zalomsky.sendto.data.repository

import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.domain.model.Client
import com.zalomsky.sendto.domain.repository.ClientRepository
import javax.inject.Inject

class ClientRepositoryImpl@Inject constructor(

    private val database: FirebaseDatabase,
    private val databaseUidReference: DatabaseReference,
    private val auth: FirebaseAuth,
): ClientRepository {
    override suspend fun add(id: String, email: String, phone: String, view: View, addressBookId: String) {
        try {
            val client = Client(id, email, phone)
            databaseUidReference.child(FirebaseConstants.ADDRESS_BOOK_KEY).child(addressBookId).child(FirebaseConstants.CLIENTS_KEY).child(id).setValue(client).addOnSuccessListener {
            }
        }catch (e: Exception){
            e.stackTrace
        }
    }
}