package com.example.domain.usecase

import com.example.domain.models.Product
import com.example.domain.repository.Repository

class DeleteProductFavoritesDbUseCase(private val repository: Repository) {
    suspend operator fun invoke(product: Product){
        repository.deleteProductFromDataBase(product)
    }
}