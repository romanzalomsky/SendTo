package com.zalomsky.sendto.domain.usecase

import android.view.View
import com.zalomsky.sendto.domain.repository.AccountRepository
import javax.inject.Inject

class GetUsersInfoUseCase@Inject constructor(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(view: View){
        accountRepository.getUsersData(view)
    }
}