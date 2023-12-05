package com.zalomsky.sendto.presentation.common.auth

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zalomsky.sendto.domain.model.User
import com.zalomsky.sendto.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthFragmentViewModel@Inject constructor(
    private val authUseCase: AuthUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(User())
    val uiState: StateFlow<User> = _uiState.asStateFlow()

    fun onSignInClick(email: String, password: String, view: View){
        viewModelScope.launch {
            authUseCase(email, password, view)
        }
    }
}
