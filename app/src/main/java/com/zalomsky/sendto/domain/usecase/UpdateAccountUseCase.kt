package com.zalomsky.sendto.domain.usecase

import android.view.View
import com.zalomsky.sendto.domain.repository.AccountRepository
import javax.inject.Inject

class UpdateAccountUseCase@Inject constructor(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(name: String, phone: String, email: String, password: String){
        accountRepository.updateAccount(name, phone, email, password)
    }
}