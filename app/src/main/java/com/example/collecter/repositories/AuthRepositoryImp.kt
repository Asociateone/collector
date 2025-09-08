package com.example.collecter.repositories

import com.example.collecter.services.HTTP

interface AuthRepository
{
    suspend fun signIn(email: String, password: String): Unit
}
class AuthRepositoryImp(val http: HTTP): AuthRepository
{
    /**
     * @param email
     */
    override suspend fun signIn(email: String, password: String): Unit
    {
        http.signIn(email, password)
    }
}