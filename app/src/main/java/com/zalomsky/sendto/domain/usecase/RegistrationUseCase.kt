package com.zalomsky.sendto.domain.usecase

import com.zalomsky.sendto.domain.repository.AccountRepository
import javax.inject.Inject

class RegistrationUseCase@Inject constructor(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(name: String, email: String, phone: String, password: String){
        accountRepository.registration(name, email, phone, password)
    }
}