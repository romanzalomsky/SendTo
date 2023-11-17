package com.zalomsky.sendto.data.repository

import android.util.Log
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.domain.model.Role
import com.zalomsky.sendto.domain.model.User
import com.zalomsky.sendto.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountRepositoryImpl@Inject constructor(
    private val databaseUidReference: DatabaseReference,
    private val auth: FirebaseAuth
): AccountRepository {

    override val currentUserId: String
        get() = FirebaseAuth.getInstance().currentUser!!.uid

    override val hasUser: Boolean
        get() = auth.currentUser != null


   /* override suspend fun registration(
        name: String,
        phone: String,
        email: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){

                database.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("name").setValue(name)
                database.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("phone").setValue(phone)
                database.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("email").setValue(email)
                database.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("password").setValue(password)

            }
            else {
                Log.e("error: ", it.exception.toString())
            }
        }.await()
    }*/

    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun deleteAccount() {
        auth.currentUser?.delete()?.await()
    }

    override suspend fun updateAccount(
        name: String,
        email: String,
        phone: String,
        password: String
    ) {
        val user = User(currentUserId, name, phone, email, password)
        databaseUidReference.updateChildren(user.toMap())
    }


    override suspend fun signOut() {
        auth.signOut()
    }

/*    private suspend fun getCurrentUserRole(): Role =
        runCatching {


        }.getOrDefault(Role.USER)*/
}