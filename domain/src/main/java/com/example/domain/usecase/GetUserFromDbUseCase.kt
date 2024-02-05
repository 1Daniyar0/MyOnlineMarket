package com.example.domain.usecase

import com.example.domain.models.Product
import com.example.domain.models.User
import com.example.domain.repository.Repository

class GetUserFromDbUseCase(private val repository: Repository) {
    suspend operator fun invoke(): User {
        return repository.getUserFromDb()
    }
}