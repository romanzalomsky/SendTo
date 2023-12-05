package com.zalomsky.sendto.data.repository

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.domain.model.EmailMessages
import com.zalomsky.sendto.domain.repository.EmailMessageRepository
import javax.inject.Inject

class EmailMessageRepositoryImpl@Inject constructor(
    private val database: FirebaseDatabase,
    private val databaseUidReference: DatabaseReference,
    private val auth: FirebaseAuth,
): EmailMessageRepository {

    override suspend fun send(
        id: String,
        to: String,
        from: String,
        message: String,
        selectedName: String,
        selectedId: String
    ) {
        val emailMessage = EmailMessages(id, to, from, message, selectedName, selectedId)
        databaseUidReference.child(FirebaseConstants.MESSAGE_KEY).child(id).setValue(emailMessage)
    }
}