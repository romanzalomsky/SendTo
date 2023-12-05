package com.zalomsky.sendto.data.repository

import com.google.firebase.database.DatabaseReference
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.domain.model.Template
import com.zalomsky.sendto.domain.repository.TemplateRepository
import javax.inject.Inject

class TemplateRepositoryImpl@Inject constructor(
    private val databaseReference: DatabaseReference
): TemplateRepository {

    override suspend fun add(id: String, name: String, text: String) {

        val template = Template(id, name, text)

        try {
            databaseReference.child(FirebaseConstants.TEMPLATE_KEY).child(id).setValue(template)
        }catch (e: Exception){
            e.stackTrace
        }
    }
}