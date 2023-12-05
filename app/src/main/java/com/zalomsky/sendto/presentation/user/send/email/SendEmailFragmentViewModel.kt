package com.zalomsky.sendto.presentation.user.send.email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zalomsky.sendto.domain.usecase.SendEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendEmailFragmentViewModel@Inject constructor(
    private val sendEmailUseCase: SendEmailUseCase
): ViewModel() {

    fun onSendEmail(
        id: String,
        to: String,
        from: String,
        message: String,
        selectedName: String,
        selectedId: String
    ){
        viewModelScope.launch {
            sendEmailUseCase(id, to, from, message, selectedName, selectedId)
        }
    }
}