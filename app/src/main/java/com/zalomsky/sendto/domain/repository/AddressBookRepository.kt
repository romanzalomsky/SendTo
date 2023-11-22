package com.zalomsky.sendto.domain.repository

interface AddressBookRepository {

    suspend fun add(id: String, name: String)
}