package com.zalomsky.sendto.domain.repository

interface TemplateRepository {

    suspend fun add(id: String, name: String, text: String)
}