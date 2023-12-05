package com.zalomsky.sendto.domain.model

data class SmsMessage (

    val id: String? = null,
    val to: String? = null,
    val from: String? = null,
    val message: String? = null,
/*    val addressBook: String? = null,
    val addressBookId: String? = null*/
)