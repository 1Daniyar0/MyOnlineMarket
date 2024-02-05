package com.example.domain.repository

import com.example.domain.models.ListProduct
import com.example.domain.models.Product
import com.example.domain.models.User

interface Repository{
    suspend fun addUserToDataBase(user: User)
    suspend fun checkUserDataBase():Boolean
    suspend fun getListOfProduct():ListProduct
    suspend fun addProductToDataBase(product: Product)
    suspend fun deleteProductFromDataBase(product: Product)
    suspend fun getProductFromDataBase()
}