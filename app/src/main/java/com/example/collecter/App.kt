package com.example.collecter

import android.app.Application
import com.example.collecter.modules.HTTP
import com.example.collecter.repositories.AuthRepositoryImp
import com.example.collecter.repositories.AuthRepository
import com.example.collecter.ui.models.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

class App : Application() {
    val appModule = module {
        singleOf(::HTTP)
        singleOf(::AuthRepositoryImp) bind AuthRepository::class
        viewModelOf(::AuthViewModel)
    }

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }
}