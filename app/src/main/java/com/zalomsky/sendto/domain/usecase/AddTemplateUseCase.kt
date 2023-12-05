package com.zalomsky.sendto.domain.usecase

import com.zalomsky.sendto.domain.repository.TemplateRepository
import javax.inject.Inject

class AddTemplateUseCase@Inject constructor(
    private val templateRepository: TemplateRepository
) {

    suspend operator fun invoke(id: String, name: String, text: String){
        templateRepository.add(id, name, text)
    }
}