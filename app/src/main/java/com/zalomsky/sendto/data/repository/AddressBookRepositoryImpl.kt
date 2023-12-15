package com.zalomsky.sendto.data.repository

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.domain.model.AddressBook
import com.zalomsky.sendto.domain.repository.AddressBookRepository
import javax.inject.Inject

class AddressBookRepositoryImpl@Inject constructor(
    private val databaseReference: DatabaseReference
): AddressBookRepository {

    override suspend fun add(id: String, name: String) {

        val addressBook = AddressBook(id, name)

        databaseReference.child(FirebaseConstants.ADDRESS_BOOK_KEY).child(id).setValue(addressBook)
    }

    override suspend fun getAmount(amount: String) {
        databaseReference.child(FirebaseConstants.ADDRESS_BOOK_KEY)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override suspend fun delete(id: String) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}