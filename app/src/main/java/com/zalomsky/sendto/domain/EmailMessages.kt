package com.zalomsky.sendto.domain

import java.util.Date

data class EmailMessages(

    val id: String,
    val to: String,
    val from: String,
    val message: String
)
