package com.example.domain.usecase

import com.example.domain.models.User
import com.example.domain.repository.Repository

class CheckUserInDataBaseUseCase(private val repository: Repository) {
    suspend operator fun invoke(user: User):Boolean{
        return repository.checkUserDataBase(user)
    }
}