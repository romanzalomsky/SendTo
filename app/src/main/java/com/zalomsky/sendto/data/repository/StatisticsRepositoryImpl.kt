package com.zalomsky.sendto.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zalomsky.sendto.domain.repository.StatisticsRepository
import javax.inject.Inject

class StatisticsRepositoryImpl@Inject constructor(
    private val database: FirebaseDatabase,
    private val databaseUidReference: DatabaseReference,
    private val auth: FirebaseAuth
): StatisticsRepository {
    override suspend fun getClients() {


    }
}