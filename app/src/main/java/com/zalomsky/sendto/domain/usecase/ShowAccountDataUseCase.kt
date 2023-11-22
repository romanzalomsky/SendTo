package com.zalomsky.sendto.domain.usecase

import com.zalomsky.sendto.domain.repository.AccountRepository
import javax.inject.Inject

class ShowAccountDataUseCase@Inject constructor(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(name: String, email: String){
        accountRepository.showAccount(name, email)
    }
}