package com.example.domain.usecase

import com.example.domain.models.ListProduct
import com.example.domain.repository.Repository

class GetListOfProductUseCase(private val repository: Repository) {
    suspend operator fun invoke():ListProduct{
        return repository.getListOfProduct()
    }
}