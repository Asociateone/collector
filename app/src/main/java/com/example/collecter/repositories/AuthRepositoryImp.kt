package com.example.collecter.repositories

import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.collecter.modules.HTTP
import kotlinx.coroutines.runBlocking

interface AuthRepository
{
    suspend fun signIn(test: String): Unit
}
class AuthRepositoryImp(val http: HTTP): AuthRepository
{
    /**
     * @param test
     */
    override suspend fun signIn(test: String): Unit
    {
        http.check()

        Log.d("test", test)
    }
}