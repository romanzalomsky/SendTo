package com.zalomsky.sendto.domain.repository

interface EmailMessageRepository {

    suspend fun send(id: String, to: String, from: String, message: String, selectedName: String, selectedId: String)
}