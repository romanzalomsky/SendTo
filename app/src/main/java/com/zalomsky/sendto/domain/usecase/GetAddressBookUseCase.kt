package com.zalomsky.sendto.domain.usecase

import com.zalomsky.sendto.domain.repository.AddressBookRepository
import javax.inject.Inject

class GetAddressBookUseCase@Inject constructor(
    private val addressBookRepository: AddressBookRepository
) {

    suspend operator fun invoke(amount: String){
        addressBookRepository.getAmount(amount)
    }
}