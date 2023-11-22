package com.zalomsky.sendto.domain.model


data class EmailMessages(

    val id: String,
    val to: String,
    val from: String,
    val message: String
)
