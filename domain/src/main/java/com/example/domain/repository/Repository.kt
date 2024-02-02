package com.example.domain.repository

import com.example.domain.models.ListProduct
import com.example.domain.models.User

interface Repository{
    suspend fun addUserToDatabase(user: User)
    suspend fun checkUserDataBase():Boolean
    suspend fun getListOfProduct():ListProduct
}