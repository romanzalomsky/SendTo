package com.zalomsky.sendto.domain.usecase

import com.zalomsky.sendto.domain.repository.EmailMessageRepository
import javax.inject.Inject

class SendEmailUseCase@Inject constructor(
    private val emailMessageRepository: EmailMessageRepository
) {
    suspend operator fun invoke(
        id: String,
        to: String,
        from: String,
        message: String,
        selectedName: String,
        selectedId: String
    ){
        emailMessageRepository.send(id, to, from, message, selectedName, selectedId)
    }
}