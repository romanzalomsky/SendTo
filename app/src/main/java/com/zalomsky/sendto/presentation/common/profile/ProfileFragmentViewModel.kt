package com.zalomsky.sendto.presentation.common.profile

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zalomsky.sendto.domain.usecase.GetUsersInfoUseCase
import com.zalomsky.sendto.domain.usecase.UpdateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel@Inject constructor(
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val getUsersInfoUseCase: GetUsersInfoUseCase
): ViewModel() {

    fun onUpdateButtonClick(name: String, phone: String, email: String, password: String){
        viewModelScope.launch {
            updateAccountUseCase(name, phone, email, password)
        }
    }

    fun onGetUsersData(view: View){
        viewModelScope.launch {
            getUsersInfoUseCase(view)
        }
    }
}