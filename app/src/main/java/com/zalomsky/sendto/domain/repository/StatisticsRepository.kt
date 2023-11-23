package com.zalomsky.sendto.domain.repository

interface StatisticsRepository {

    suspend fun getAddressBook()
}