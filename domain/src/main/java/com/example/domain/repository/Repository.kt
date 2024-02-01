package com.example.domain.repository

import com.example.domain.models.User

interface Repository{
    suspend fun addUserToDatabase(user: User)
    suspend fun checkUserDataBase(user: User):Boolean
}