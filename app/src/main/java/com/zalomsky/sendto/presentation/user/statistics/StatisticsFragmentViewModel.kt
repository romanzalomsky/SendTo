package com.zalomsky.sendto.presentation.user.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zalomsky.sendto.domain.usecase.GetAddressBookUseCase
import com.zalomsky.sendto.domain.usecase.ShowAccountDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsFragmentViewModel@Inject constructor(
    private val showAccountDataUseCase: ShowAccountDataUseCase,
    private val getAddressBookUseCase: GetAddressBookUseCase
): ViewModel() {

    fun showHeaderData(name: String, email: String){
        viewModelScope.launch {
            showAccountDataUseCase(name, email)
        }
    }

    fun getAmountAddressBook(amount: String){
        viewModelScope.launch {
            getAddressBookUseCase(amount)
        }
    }
}
