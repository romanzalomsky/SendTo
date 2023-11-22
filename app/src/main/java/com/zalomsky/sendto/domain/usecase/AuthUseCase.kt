package com.zalomsky.sendto.domain.usecase

import android.view.View
import com.zalomsky.sendto.domain.repository.AccountRepository
import javax.inject.Inject

class AuthUseCase@Inject constructor(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(email: String, password: String, view: View){
        accountRepository.authenticate(email, password, view)
    }
}