package com.zalomsky.sendto.presentation.user.clients.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zalomsky.sendto.domain.usecase.AddAddressBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressBookViewModel@Inject constructor(
    private val addressBookUseCase: AddAddressBookUseCase
): ViewModel() {

    fun onAddAddressBook(id: String, name: String){
        viewModelScope.launch {
            addressBookUseCase(id, name)
        }
    }

}