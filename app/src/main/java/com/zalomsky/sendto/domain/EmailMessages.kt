package com.zalomsky.sendto.domain

import java.util.Date

data class EmailMessages(

    val id: Long,
    val senderId: Long,
    val sendTime: Date,
    val email: String
)
