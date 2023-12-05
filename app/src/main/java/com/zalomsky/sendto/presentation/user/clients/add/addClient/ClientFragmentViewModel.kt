package com.zalomsky.sendto.presentation.user.clients.add.addClient

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zalomsky.sendto.domain.model.Client
import com.zalomsky.sendto.domain.usecase.AddClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientFragmentViewModel@Inject constructor(
    private val addClientUseCase: AddClientUseCase
): ViewModel() {
    fun onAddClient(id: String, email: String, phone: String, addressBookId: String, view: View){
        viewModelScope.launch {
            addClientUseCase(id, email, phone, addressBookId, view)
        }
    }
}