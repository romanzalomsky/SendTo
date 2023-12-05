package com.zalomsky.sendto.presentation.common.reg

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zalomsky.sendto.domain.usecase.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationFragmentViewModel@Inject constructor(
    private val registrationUseCase: RegistrationUseCase
): ViewModel() {

    fun onRegistrationClick(name: String, email: String, phone: String, password: String, view: View){
        viewModelScope.launch {
            registrationUseCase(name, email, phone, password, view)
        }
    }
}
