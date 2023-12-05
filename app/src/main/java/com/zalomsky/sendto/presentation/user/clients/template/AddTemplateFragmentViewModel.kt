package com.zalomsky.sendto.presentation.user.clients.template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zalomsky.sendto.domain.usecase.AddTemplateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTemplateFragmentViewModel@Inject constructor(
    private val addTemplateUseCase: AddTemplateUseCase
):ViewModel() {

    fun onAddTemplate(id: String, name: String, text: String){
        viewModelScope.launch {
            addTemplateUseCase(id, name, text)
        }
    }
}