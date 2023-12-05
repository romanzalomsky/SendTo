package com.zalomsky.sendto.domain.usecase

import android.view.View
import com.zalomsky.sendto.domain.model.Client
import com.zalomsky.sendto.domain.repository.ClientRepository
import javax.inject.Inject

class AddClientUseCase@Inject constructor(
    private val clientRepository: ClientRepository
) {

    suspend operator fun invoke(id: String, email: String, phone: String, addressBookId: String, view: View){
        clientRepository.add(id, email, phone, addressBookId, view)
    }
}