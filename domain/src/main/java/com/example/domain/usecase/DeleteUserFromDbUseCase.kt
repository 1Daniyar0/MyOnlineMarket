package com.example.domain.usecase

import com.example.domain.models.Product
import com.example.domain.repository.Repository

class DeleteUserFromDbUseCase(private val repository: Repository) {
    suspend operator fun invoke(){
        repository.deleteUserFromDb()
    }
}