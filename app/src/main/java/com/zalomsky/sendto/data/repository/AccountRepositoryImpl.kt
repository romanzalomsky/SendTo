package com.zalomsky.sendto.data.repository

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zalomsky.sendto.R
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.domain.model.User
import com.zalomsky.sendto.domain.repository.AccountRepository
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

    override suspend fun registration(name: String, email: String, phone: String, password: String, view: View) {
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                database.reference.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("name").setValue(name)
                database.reference.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("phone").setValue(phone)
                database.reference.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("email").setValue(email)
                database.reference.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("password").setValue(password)

                val user: FirebaseUser? = auth.currentUser
                user?.sendEmailVerification()

                if(user?.isEmailVerified == true){
                    Navigation.findNavController(view).navigate(R.id.action_authFragment_to_statisticsFragment)
                }
            }.await()
        } catch (e: Exception){
            e.stackTrace
        }
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

    override suspend fun deleteAccount() { auth.currentUser?.delete()?.await() }

    override suspend fun updateAccount(name: String, phone: String, email: String, password: String) {
        val user = User(currentUserId, name, phone, email, password)
        databaseUidReference.updateChildren(user.toMap())
    }

    override suspend fun showHeaderData(name: String, email: String) {
        databaseUidReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override suspend fun getUsersData(view: View) {

        val nameEdit = view.findViewById<EditText>(R.id.nameEdit)
        val emailEdit = view.findViewById<TextView>(R.id.emailEdit)
        val phoneEdit = view.findViewById<TextView>(R.id.phoneEdit)
        val passwordEdit = view.findViewById<TextView>(R.id.passwordEdit)

        databaseUidReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val name = dataSnapshot.child("name").value.toString()
                val email = dataSnapshot.child("email").value.toString()
                val phone = dataSnapshot.child("phone").value.toString()
                val password = dataSnapshot.child("password").value.toString()

                nameEdit.setText(name)
                emailEdit.setText(email)
                phoneEdit.setText(phone)
                passwordEdit.setText(password)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override suspend fun signOut() { auth.signOut() }
}