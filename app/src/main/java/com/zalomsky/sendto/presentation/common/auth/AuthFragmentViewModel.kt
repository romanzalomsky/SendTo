package com.zalomsky.sendto.presentation.common.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

open class AuthFragmentViewModel: ViewModel() {

    val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val auth: MutableLiveData<FirebaseAuth> by lazy {
        MutableLiveData<FirebaseAuth>()
    }
}
