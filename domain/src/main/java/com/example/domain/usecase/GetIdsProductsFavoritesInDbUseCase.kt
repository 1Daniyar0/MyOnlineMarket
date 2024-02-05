package com.example.domain.usecase

import com.example.domain.repository.Repository

class GetIdsProductsFavoritesInDbUseCase(private val repository: Repository) {
    suspend operator fun invoke(): ArrayList<String>{
        return repository.getIdProductFavoritesInDb()
    }
}