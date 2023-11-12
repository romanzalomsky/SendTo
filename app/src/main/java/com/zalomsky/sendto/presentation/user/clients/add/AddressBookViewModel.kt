package com.zalomsky.sendto.presentation.user.clients.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class AddressBookViewModel: ViewModel() {

    val map: MutableLiveData<Map<String, String?>> by lazy {
        MutableLiveData<Map<String, String?>>()
    }
}