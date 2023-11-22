package com.zalomsky.sendto.data.repository

import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
    private val database: FirebaseDatabase,
    private val databaseUidReference: DatabaseReference,
    private val auth: FirebaseAuth,

): AccountRepository {

    override val currentUserId: String
        get() = FirebaseAuth.getInstance().currentUser!!.uid

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override suspend fun registration(
        name: String,
        email: String,
        phone: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){

                database.reference.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("name").setValue(name)
                database.reference.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("phone").setValue(phone)
                database.reference.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("email").setValue(email)
                database.reference.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("password").setValue(password)

                val user: FirebaseUser? = auth.currentUser

                user?.sendEmailVerification()?.addOnCompleteListener {
                    if(it.isSuccessful){

                    }
                }
            }
            else {
                Log.e("error: ", it.exception.toString())
            }
        }.await()
    }

    override suspend fun authenticate(email: String, password: String, view: View) {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_statisticsFragment)
            }.await()
        }catch (e: Exception){
            e.stackTrace
        }
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

    override suspend fun showAccount(name: String, email: String) {
        databaseUidReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override suspend fun signOut() {
        auth.signOut()
    }

}