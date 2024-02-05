package com.example.domain.usecase

import com.example.domain.models.Product
import com.example.domain.repository.Repository

class GetProductListFavoritesUseCase(private val repository: Repository) {
    suspend operator fun invoke():ArrayList<Product> {
        return repository.getListProductFromDataBase()
    }
}