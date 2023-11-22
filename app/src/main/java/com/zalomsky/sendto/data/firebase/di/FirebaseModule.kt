package com.zalomsky.sendto.data.firebase.di

import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun auth(): FirebaseAuth = Firebase.auth

    @Provides
    fun firebasedatabase(): FirebaseDatabase = Firebase.database

    @Provides
    fun databaseUidReference(): DatabaseReference = FirebaseDatabase.getInstance().getReference(
        FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid)

/*    @Provides
    fun addressBookReference(): DatabaseReference = FirebaseDatabase.getInstance().getReference(
        FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid)
        .child(FirebaseConstants.ADDRESS_BOOK_KEY)*/

}