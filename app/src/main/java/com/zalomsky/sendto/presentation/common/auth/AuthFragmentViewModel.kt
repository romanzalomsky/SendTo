package com.zalomsky.sendto.presentation.common.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.zalomsky.sendto.domain.model.User
import com.zalomsky.sendto.domain.usecase.AuthUseCase
import com.zalomsky.sendto.domain.usecase.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthFragmentViewModel@Inject constructor(
    private val registrationUseCase: RegistrationUseCase
): ViewModel() {

    fun onRegistrationClick(name: String, email: String, phone: String, password: String){
        viewModelScope.launch {
            registrationUseCase(name, email, phone, password)
        }
    }
}
