package com.zalomsky.sendto.presentation.common.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zalomsky.sendto.domain.usecase.UpdateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel@Inject constructor(
    private val updateAccountUseCase: UpdateAccountUseCase
): ViewModel() {

    fun onUpdateButtonClick(
        name: String,
        email: String,
        phone: String,
        password: String
    ){
        viewModelScope.launch {
            updateAccountUseCase(name, email, phone, password)
        }
    }
}