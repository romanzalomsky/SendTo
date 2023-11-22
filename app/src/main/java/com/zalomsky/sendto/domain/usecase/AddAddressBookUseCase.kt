package com.zalomsky.sendto.domain.usecase

import com.zalomsky.sendto.domain.repository.AddressBookRepository
import javax.inject.Inject

class AddAddressBookUseCase@Inject constructor(
    private val addressBookRepository: AddressBookRepository
) {

    suspend operator fun invoke(id: String, name: String){
        addressBookRepository.add(id, name)
    }
}